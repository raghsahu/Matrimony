package ics.hindu.matrimony.interfaces;

public interface Consts {
    public static String APP_NMAE = "Matrimony";

    public static String BASE_URL = "http://phplaravel-132936-535459.cloudwaysapps.com/api/";
    public static String IMAGE_URL = "http://phplaravel-132936-535459.cloudwaysapps.com";

    /*APIs*/

    String REGISTRATION = "register";
    String LOGIN_API = "login";
    String MY_PROFILE_API = "me";
    String ALL_PROFILES_API = "users";
    String UPDATE_PROFILE_API = "update";
    String JUST_JOIN_API = "just-join";
    String SET_SHORTLISTED_API = "set-shortlisted";
    String REMOVE_SHORTLISTED_API = "remove-shortlisted";
    String IMAGES = "images";
    String GET_GALLARY_API = "get-gallary";
    String SHORTLISTED_API = "shortlisted";
    String RESEND_OTP_API = "resend-otp";
    String GET_VISITORS_API = "get-visitors";
    String SET_VISITOR_API = "set-visitor";
    String SET_AVATAR_API = "set-avatar";
    String DELETE_IMAGE_API = "delete-image";
    String GET_EVENTS_API = "get-events";
    String CHANGE_PASSWORD_API = "password-reset";
    String SEND_INTEREST_API = "send-interest";
    String FORGET_PASSWORD_API = "forget-password";
    String UPDATE_INTEREST_API = "update-interest";
    String GET_INTEREST_API = "get-interest";
    String SEARCH_API = "search";
    String GET_ALL_PACKAGES_API = "getAllPackages";
    String SUBSCRIPTION_API = "subscription";
    String GET_MY_SUBSCRIPTION_HISTORY_API = "get_my_subscription_history";
    String GET_CHAT_HISTORY_API = "getChatHistory";
    public String GET_CHAT_API = "getChat";
    public String SEND_CHAT_API = "sendmsg";
    public String GET_MY_SUBSCRIPTION_API = "get_my_subscription";

    /*DTOs*/

    String LOGIN_DTO = "login_dto";
    String USER_DTO = "user_dto";
    String SUBSCRIPTION_DTO = "subscription_dto";
    String SEARCH_PARAM = "search_param";

    /*Project*/
    String IS_REGISTERED = "is_registered";
    String CAMERA_ACCEPTED = "camera_accepted";
    String MODIFY_AUDIO_ACCEPTED = "modify_audio_accepted";
    String CALL_PRIVILAGE = "call_privilage";
    String READ_SMS = "read_sms";
    String RECEIVE_SMS = "read_sms";
    String FINE_LOC = "fine_loc";
    String CORAS_LOC = "coras_loc";
    String CALL_PHONE = "call_phone";
    String STORAGE_ACCEPTED = "storage_accepted";
    String IMAGE_LIST = "image_list";
    String WEB_VIEW_FLAG = "web_view_flag";
    String LANGUAGE_SELECTION = "language_selection";
    String SELECTED_LANGUAGE = "selected_language";
    String TAG_PROFILE = "tag_profile";
    String LANGUAGE_PREF = "language_pref";
    String IS_SUBSCRIBE = "is_subscribe";


    /*registration params*/
    String NAME = "name";
    String EMAIL = "email";
    String GENDER = "gender";
    String PROFILE_FOR = "profile_for";
    String MARITAL_STATUS = "marital_status";
    String AADHAAR = "aadhaar";
    String PASSWORD = "password";
    String BLOOD_GROUP = "blood_group";
    String DOB = "dob";
    String BIRTH_PLACE = "birth_place";
    String BIRTH_TIME = "birth_time";
    String HEIGHT = "height";
    String MANGLIK = "manglik";
    String QUALIFICATION = "qualification";
    String OCCUPATION = "occupation";
    String INCOME = "income";
    String WORK_PLACE = "work_place";
    String STATE = "state";
    String DISTRICT = "district";
    String MOBILE = "mobile";
    String PIN = "pin";
    String CITY = "city";
    String GOTRA = "gotra";
    String CASTE = "caste";
    String GOTRA_NANIHAL = "gotra_nanihal";
    String ORGANIZATION = "organisation_name";
    String OTP = "otp";
    String WORKING = "working";
    String FIREBASE_TOKEN = "firebase_token";


    /*Life Style*/
    String DIETARY = "dietary";
    String DRINKING = "drinking";
    String SMOKING = "smoking";
    String LANGUAGE = "language";
    String HOBBIES = "hobbies";
    String INTERESTS = "interests";

    /*About Family*/
    String MOTHER_OCCUPATION = "mother_occupation";
    String FATHER_OCCUPATION = "father_occupation";
    String FAMILY_INCOME = "family_income";
    String FAMILY_STATUS = "family_status";
    String FAMILY_TYPE = "family_type";
    String FAMILY_VALUE = "family_value";
    String FAMILY_STATE = "family_state";
    String FAMILY_DISTRICT = "family_district";
    String FAMILY_CITY = "family_city";
    String FAMILY_PIN = "family_pin";
    String BROTHER = "brother";
    String SISTER = "sister";
    String GRAND_FATHER_NAME = "grand_father_name";
    String MATERNAL_GRAND_FATHER_NAME_ADDRESS = "maternal_grand_father_name_address";
    String FATHER_NAME = "father_name";
    String MOTHER_NAME = "mother_name";
    String PERMANENT_ADDRESS = "permanent_address";
    String WHATSAPP_NO = "whatsapp_no";
    String MOBILE2 = "mobile2";

    /*About Me*/
    String ABOUT_ME = "about_me";
    String WEIGHT = "weight";
    String BODY_TYPE = "body_type";
    String COMPLEXION = "complexion";
    String CHALLENGED = "challenged";
    //String BLOOD_GROUP = "blood_group";

    /*Basic Details*/
    //String NAME = "name";
    //String HEIGHT = "height";
//    String STATE = "state";
//    String DISTRICT = "district";
//    String CITY = "city";
//    String INCOME = "income";

    /*Important Details*/
//    String QUALIFICATION = "qualification";
//    String WORK_PLACE = "work_place";
//    String OCCUPATION = "occupation";
//    String ORGANIZATION = "organisation_name";

    /*My Profile get*/
    //String MOBILE = "mobile";
    String TOKEN = "token";
    String LANG = "lang";

    /*Get All Profile*/
    String USER_ID = "user_id";
//    String TOKEN = "token";
//    String GENDER = "gender";

    /*Critical*/

    String CRITICAL = "critical";

    /*shortListed*/

    String SHORT_LISTED_ID = "short_listed_id";

    /*setVisitor*/
    String VISITOR_ID = "visitor_id";
    /*setProfilePic*/
    String MEDIA_ID = "media_id";

    /*verified user*/
    String IS_ACTIVE = "is_active";

    /*send interest*/
    String REQUESTED_ID = "requested_id";

    /*change password*/
    String OLD_PASSWORD = "old_password";
    String NEW_PASSWORD = "new_password";

    /*get interest*/
    String TYPE = "type";
    String SENT = "sent";
    String RECIEVED = "recieved";

    /*Buy Package*/
    String PACKAGE_ID = "package_id";
    String TXN_ID = "txn_id";
    String ORDER_ID = "order_id";


    String RECEIVER_ID = "receiver_id";
    String SENDER_ID = "sender_id";
    String SENDER_NAME = "sender_name";
    String MESSAGE = "message";
    String CHAT_TYPE = "chat_type";
    String CALLING_STATUS = "calling_status";
    String ROOM_ID = "room_id";
    String USER_NAME = "user_name";
    String PIC_URL = "pic_url";
    String BROADCAST = "broadcast";
    String IMAGE_URI_CAMERA = "image_uri_camera";
    String IMAGE = "image";
}
