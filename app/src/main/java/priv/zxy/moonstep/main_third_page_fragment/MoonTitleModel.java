package priv.zxy.moonstep.main_third_page_fragment;

import android.os.Parcel;
import android.os.Parcelable;

public class MoonTitleModel implements Parcelable {
    private String titleName;
    private String titleLevel;
    private int imageResId;

    private int backgroundcolorResId;

    private String titleDescription;

    private MoonTitleModel(Builder builder){
        titleName = builder.titleName;
        titleLevel = builder.titleLevel;
        imageResId = builder.imageResId;
        backgroundcolorResId = builder.backgroundColorResId;
        titleDescription = builder.titleDescription;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public String getTitleName(){
        return titleName;
    }

    public String getTitleLevel(){
        return titleLevel;
    }

    public int getImageResId(){
        return imageResId;
    }

    public int getBackgroundColorResId(){
        return backgroundcolorResId;
    }

    public String getTitleDescription(){
        return titleDescription;
    }

    /**
     * constructor for Parcelable implementation
     * @param parcel
     */
    private MoonTitleModel(Parcel parcel){
        titleName = parcel.readString();
        titleLevel = parcel.readString();
        imageResId = parcel.readInt();
        backgroundcolorResId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titleName);
        parcel.writeString(titleLevel);
        parcel.writeInt(imageResId);
        parcel.writeInt(backgroundcolorResId);

    }

    @SuppressWarnings("unused")
    public final static Creator<MoonTitleModel> CREATOR = new Creator<MoonTitleModel>() {
        @Override
        public MoonTitleModel createFromParcel(Parcel parcel) {
            return new MoonTitleModel(parcel);
        }

        @Override
        public MoonTitleModel[] newArray(int size) {
            return new MoonTitleModel[size];
        }
    };

    /**
     * {@code SportCardModel} builder static inner class.
     */
    public static final class Builder{
        private String titleName;
        private String titleLevel;
        private int imageResId;
        private int backgroundColorResId;
        private String titleDescription;

        private Builder(){
        }

        /**
         * Sets the {@code titleName} and returns a reference
         *
         * @param titleName the {@code titleName} to set
         * @return a reference to this Builder
         */
        public Builder withTitleName(String titleName){
            this.titleName = titleName;
            return this;
        }

        /**
         * Sets the {@code titleLevel} and returns a reference
         *
         * @param titleLevel the {@code titleLevel} to set
         * @return a reference to this Builder
         */
        public Builder withTitleLevel(String titleLevel){
            this.titleLevel = titleLevel;
            return this;
        }

        /**
         * Sets the {@code imageResId} and returns a reference
         *
         * @param imageResId the {@code imageResId} to set
         * @return a reference to this Builder
         */
        public Builder withImageResId(int imageResId){
            this.imageResId = imageResId;
            return this;
        }

        /**
         * Sets the {@code backgroundColorResId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param backgroundColorResId the {@code backgroundColorResId} to set
         * @return a reference to this Builder
         */
        public Builder withBackgroundColorResId(int backgroundColorResId) {
            this.backgroundColorResId = backgroundColorResId;
            return this;
        }

        /**
         * Sets the {@code titleDescription} and returns a reference
         *
         * @param titleDescription the {@code titleDescription} to set
         * @return a reference to this Builder
         */
        public Builder withTitleDescription(String titleDescription){
            this.titleDescription = titleDescription;
            return this;
        }

        /**
         * Returns a {@code MoonTitleModel} built from the parameters previously set.
         *
         * @return a {@code MoonTitleModel} built with parameters of this {@code MoonTitleModel.Builder}
         */
        public MoonTitleModel build() {
            return new MoonTitleModel(this);
        }
    }
}
