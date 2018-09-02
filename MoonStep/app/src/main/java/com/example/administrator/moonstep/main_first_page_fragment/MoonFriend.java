package com.example.administrator.moonstep.main_first_page_fragment;

public class MoonFriend{
    private int user_photo;
    private int user_gender;//0 为女性， 1 为男性
    private String user_name;
    private String user_info;

    public MoonFriend(int userPhoto, int userGender, String userName, String userInfo){
        this.user_gender = userGender;
        this.user_photo = userPhoto;
        this.user_name = userName;
        this.user_info= userInfo;
    }

    public int getUserphoto(){
        return this.user_photo;
    }

    /**
     * @return 0 为女性，1 为男性
     */
    public int getUserGender(){
        return this.user_gender;
    }

    public String getUserName(){
        return this.user_name;
    }

    public String getUserInfo() {
        return this.user_info;
    }

}
