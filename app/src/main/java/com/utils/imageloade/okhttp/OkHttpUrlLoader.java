package com.utils.imageloade.okhttp;


import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * A simple model loader for fetching media over http/https using OkHttp.
 */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final Call.Factory client;

    public OkHttpUrlLoader(Call.Factory client) {
        this.client = client;
    }

    public boolean handles(GlideUrl url) {
        return true;
    }

    @Override
    public LoadData<InputStream> buildLoadData(GlideUrl model, int width, int height, Options options) {
        //返回LoadData对象，泛型为InputStream
        return new LoadData(model, new OkHttpStreamFetcher(this.client, model));
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile Call.Factory internalClient;
        private Call.Factory client;

        private static Call.Factory getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = new OkHttpClient();
                    }
                }
            }

            return internalClient;
        }

        public Factory() {
            this(getInternalClient());
        }

        public Factory(Call.Factory client) {
            this.client = client;
        }

        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(this.client);
        }

        public void teardown() {
        }

    }
}

