package com.github.mailerific.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MailList {

    public static final int REC_PER_PAGE = 5;

    private Long tail;
    private final Listener listener;
    private final String email;
    private int lastPage;
    private int lastLoadedPage;
    private int currentPage;
    private final List<Mail> cache = new ArrayList<Mail>();
    private final RetrievalStrategy retriever;
    private final AsyncCallback<List<Mail>> callback = new DefaultCallback<List<Mail>>() {

        @Override
        public void onSuccess(final List<Mail> result) {
            if (result.size() < REC_PER_PAGE + 1) {
                lastPage = currentPage;
            }
            lastLoadedPage = currentPage;
            if (result.size() > 0) {
                tail = result.get(result.size() - 1).getId();
                cache.addAll(result);
            }
            loadFromCache();
        }

    };

    public MailList(final UserAccount user, final RetrievalStrategy retriever,
            final Listener listener) {
        this.listener = listener;
        this.email = user.getEmail();
        this.retriever = retriever;
    }

    public void nextPage() {
        currentPage++;
        retrieve();
    }

    public void previousPage() {
        currentPage--;
        loadFromCache();
    }

    public void currentPage() {
        if (tail == null) { // if this is first request, load
            currentPage = 1;
            retrieve();
        } else {
            loadFromCache();
        }
    }

    private void loadFromCache() {
        int startIndex = (currentPage - 1) * REC_PER_PAGE;
        List<Mail> view = subList(startIndex, startIndex + REC_PER_PAGE);
        listener.afterRetrieve(view);
    }

    /**
     * necessary because GWT doesn't implement {@link List#subList(int, int)}
     * 
     * @param fromIndex
     *            low endpoint (inclusive) of the subList
     * @param toIndex
     *            high endpoint (exclusive) of the subList
     * @return new list containing items starting from fromIndex to, but not
     *         including, toIndex
     */
    private List<Mail> subList(final int fromIndex, final int toIndex) {
        ArrayList<Mail> ret = new ArrayList<Mail>();
        int size = cache.size();
        for (int i = fromIndex; i < toIndex && i < size; i++) {
            ret.add(cache.get(i));
        }
        return ret;
    }

    public boolean hasPreviousPage() {
        return currentPage != 1;
    }

    public boolean hasNextPage() {
        return currentPage != lastPage;
    }

    public void reset() {
        currentPage = 0;
        tail = null;
        lastPage = 0;
        lastLoadedPage = 0;
        cache.clear();
    }

    private void retrieve() {
        if (tail == null) {// first request, start from bottom
            retriever.retrieveInitial(email, callback);
        } else if ((currentPage - 1) == lastLoadedPage) {
            retriever.retrieveNext(email, tail, callback);
        } else {
            loadFromCache();
        }
    }

    public static interface Listener {
        void afterRetrieve(List<Mail> mails);
    }

    private static interface RetrievalStrategy {
        void retrieveInitial(String email, AsyncCallback<List<Mail>> callback);

        void retrieveNext(String email, Long lastRetrieved,
                AsyncCallback<List<Mail>> callback);
    }

    public static final RetrievalStrategy INCOMING = new RetrievalStrategy() {

        @Override
        public void retrieveInitial(final String email,
                final AsyncCallback<List<Mail>> callback) {
            UserAccountServiceAsync.RPC.listIncoming(email, callback);
        }

        @Override
        public void retrieveNext(final String email, final Long lastRetrieved,
                final AsyncCallback<List<Mail>> callback) {
            UserAccountServiceAsync.RPC.listIncoming(email, lastRetrieved,
                    callback);
        }

    };

    public static final RetrievalStrategy OUTGOING = new RetrievalStrategy() {

        @Override
        public void retrieveInitial(final String email,
                final AsyncCallback<List<Mail>> callback) {
            UserAccountServiceAsync.RPC.listOutgoing(email, callback);
        }

        @Override
        public void retrieveNext(final String email, final Long lastRetrieved,
                final AsyncCallback<List<Mail>> callback) {
            UserAccountServiceAsync.RPC.listOutgoing(email, lastRetrieved,
                    callback);
        }

    };

}
