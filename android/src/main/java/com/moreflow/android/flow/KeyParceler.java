package com.moreflow.android.flow;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import com.moreflow.core.controller.Controller;
import com.moreflow.core.controller.StateController;
import com.moreflow.core.navigation.StateKey;

/**
 * Serializes/Deserializes View keys using GSON
 * Created by Stephen Moyer on 2/9/2017
 */
public class KeyParceler implements flow.KeyParceler {

    @NonNull
    @Override
    public Parcelable toParcelable(@NonNull Object key) {
        if (key instanceof StateKey) {
            return new KeyParcelable((StateKey) key);
        } else if (key instanceof StateController) {
            //TODO idk if this will ever be called
            return new KeyParcelable(new StateKey((Class<Controller>) key.getClass(), ((StateController) key).getState()));
        } else if (key instanceof Controller) {
            //TODO idk if this will ever be called
            return new KeyParcelable((Class<Controller>) key.getClass());
        } else {
            return new KeyParcelable((Class<Controller>)key);
        }
    }

    @NonNull
    @Override
    public Object toKey(@NonNull Parcelable parcelable) {
        return ((KeyParcelable) parcelable).getKey();
    }

    public static class KeyParcelable implements Parcelable {

        private static final Gson gson = new Gson();

        private final Class<Controller> controllerClass;
        private final Object state;

        KeyParcelable(StateKey key) {
            this.controllerClass = key.getController();
            this.state = key.getState();
        }

        KeyParcelable(Class<Controller> key) {
            this.controllerClass = key;
            this.state = null;
        }

        KeyParcelable(Parcel in) throws ClassNotFoundException {
            this.controllerClass = (Class<Controller>) Class.forName(in.readString());
            if (in.readByte() == 1) {
                String className = in.readString();
                this.state = gson.fromJson(in.readString(), Class.forName(className));
            } else {
                this.state = null;
            }
        }

        @Override
        public int describeContents() {
            return controllerClass.hashCode();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(controllerClass.getCanonicalName());
            if (state != null) {
                dest.writeByte((byte) 1);
                dest.writeString(state.getClass().getCanonicalName());
                dest.writeString(gson.toJson(state));
            } else {
                dest.writeByte((byte) 0);
            }
        }

        public static final Creator<KeyParcelable> CREATOR
                = new Creator<KeyParcelable>() {

            public KeyParcelable createFromParcel(Parcel in) {
                try {
                    return new KeyParcelable(in);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public KeyParcelable[] newArray(int size) {
                return new KeyParcelable[size];
            }

        };

        public Object getKey() {
            return state == null ? controllerClass : new StateKey(controllerClass, state);
        }
    }
}
