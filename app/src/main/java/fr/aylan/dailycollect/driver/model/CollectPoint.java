package fr.aylan.dailycollect.driver.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectPoint implements Parcelable {

    // The id of the Client, owner of this point collect
    String clientId;
    String name;
    // ( MinTime + MaxTime) /2
    // ex : ( 5h00 and 6h00 ) = ~5h30
    String approximativeTime;


    public CollectPoint(String clientId, String name, String approximativeTime) {
        this.clientId = clientId;
        this.name = name;
        this.approximativeTime = approximativeTime;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApproximativeTime() {
        return approximativeTime;
    }

    public void setApproximativeTime(String approximativeTime) {
        this.approximativeTime = approximativeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientId);
        dest.writeString(name);
        dest.writeString(approximativeTime);

    }

    public CollectPoint(Parcel in) {
        clientId = in.readString();
        name = in.readString();
        approximativeTime = in.readString();    }

    public static final Parcelable.Creator<CollectPoint> CREATOR = new Parcelable.Creator<CollectPoint>() {
        public CollectPoint createFromParcel(Parcel in) {
            return new CollectPoint(in);
        }

        public CollectPoint[] newArray(int size) {
            return new CollectPoint[size];
        }
    };


}
