package com.example.administrator.moonstep.main_fourth_page_fragment;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskInfo implements Parcelable {
    private String taskName;
    private String taskLevel;

    private TaskInfo(TaskInfo.Builder builder){
        taskName = builder.taskName;
        taskLevel = builder.taskLevel;
    }

    public static TaskInfo.Builder newBuilder(){
        return new TaskInfo.Builder();
    }

    public String getTaskName(){
        return taskName;
    }

    public String getTaskLevel(){
        return taskLevel;
    }

    /**
     * constructor for Parcelable implementation
     * @param parcel
     */
    private TaskInfo(Parcel parcel){
        taskName = parcel.readString();
        taskLevel = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(taskName);
        parcel.writeString(taskLevel);
    }

    @SuppressWarnings("unused")
    public final static Creator<TaskInfo> CREATOR = new Creator<TaskInfo>() {
        @Override
        public TaskInfo createFromParcel(Parcel parcel) {
            return new TaskInfo(parcel);
        }

        @Override
        public TaskInfo[] newArray(int size) {
            return new TaskInfo[size];
        }
    };

    /**
     * {@code SportCardModel} builder static inner class.
     */
    public static final class Builder{
        private String taskName;
        private String taskLevel;

        private Builder(){
        }

        /**
         * Sets the {@code taskName} and returns a reference
         *
         * @param taskName the {@code taskName} to set
         * @return a reference to this Builder
         */
        public TaskInfo.Builder withTaskName(String taskName){
            this.taskName = taskName;
            return this;
        }

        /**
         * Sets the {@code titleLevel} and returns a reference
         *
         * @param taskLevel the {@code titleLevel} to set
         * @return a reference to this Builder
         */
        public TaskInfo.Builder withTaskLevel(String taskLevel){
            this.taskLevel = taskLevel;
            return this;
        }

        /**
         * Returns a {@code TaskInfo} built from the parameters previously set.
         *
         * @return a {@code TaskInfo} built with parameters of this {@code TaskInfo.Builder}
         */
        public TaskInfo build() {
            return new TaskInfo(this);
        }
    }
}
