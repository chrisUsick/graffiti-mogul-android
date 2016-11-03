package com.cu_dev.graffitimogul.web;

import android.os.AsyncTask;

/**
 * Created by chris on 03-Nov-16.
 */
public abstract class AbstractRequest<Options, Return> extends AsyncTask<Options, Void, Return> {
    private final RequestCallback<Return> callback;
    private final int requestId;

    public interface RequestCallback<Return> {
        void onRequest(int requestId, Return value);
    }
    public AbstractRequest(int requestId, RequestCallback<Return> callback) {
        this.callback = callback;
        this.requestId = requestId;
    }
    @Override
    @SafeVarargs
    final protected Return doInBackground(Options... params) {
        return makeRequest(params[0]);

    }

    @Override
    protected void onPostExecute(Return t) {
        super.onPostExecute(t);
        callback.onRequest(requestId, t);

    }



    public abstract Return makeRequest(Options params);
}
