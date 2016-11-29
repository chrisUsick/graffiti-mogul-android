package com.cu_dev.graffitimogul.web;

import com.cu_dev.graffitimogul.domain.Tag;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chris on 03-Nov-16.
 */

public class FetchTags extends AbstractRequest<FetchTags.Options, List<Tag>> {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    static public class Options {
        public Options() {

        }
    }

    public FetchTags(int requestId, RequestCallback<List<Tag>> callback) {
        super(requestId, callback);
    }

    @Override
    public List<Tag> makeRequest(Options params) {
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("160 Princess Ave.");
        tag.setLatLng(new LatLng(49.8989, -97.1397));
        tag.setPrice(10.32);
        tags.add(tag);
        return tags;
    }
}
