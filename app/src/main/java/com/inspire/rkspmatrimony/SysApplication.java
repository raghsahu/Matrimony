package com.inspire.rkspmatrimony;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.inspire.rkspmatrimony.Models.CommanDTO;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

public class SysApplication extends Application {
    private static Context context;
    public static final String TAG = "SysApplication";
    private static SysApplication instance;

    public static SysApplication getInstance() {
        return instance;
    }

    ArrayList<CommanDTO> hobbiesList = new ArrayList<>();
    ArrayList<CommanDTO> heightList = new ArrayList<>();
    ArrayList<CommanDTO> incomeList = new ArrayList<>();
    ArrayList<CommanDTO> bloodList = new ArrayList<>();
    ArrayList<CommanDTO> maritalList = new ArrayList<>();
    ArrayList<CommanDTO> bodytypelist = new ArrayList<>();
    ArrayList<CommanDTO> complexionlist = new ArrayList<>();
    ArrayList<CommanDTO> challengedlist = new ArrayList<>();
    ArrayList<CommanDTO> manglikList = new ArrayList<>();
    ArrayList<CommanDTO> Famailystatuslist = new ArrayList<>();
    ArrayList<CommanDTO> Familytypelist = new ArrayList<>();
    ArrayList<CommanDTO> Familyvalueslist = new ArrayList<>();
    ArrayList<CommanDTO> occupationList = new ArrayList<>();
    ArrayList<CommanDTO> motheroccupationList = new ArrayList<>();
    ArrayList<CommanDTO> fatheroccupationList = new ArrayList<>();
    ArrayList<CommanDTO> interestsList = new ArrayList<>();
    ArrayList<CommanDTO> dietaryList = new ArrayList<>();
    ArrayList<CommanDTO> drinkingList = new ArrayList<>();
    ArrayList<CommanDTO> smokingList = new ArrayList<>();
    ArrayList<CommanDTO> lanuageList = new ArrayList<>();
    ArrayList<CommanDTO> brotherList = new ArrayList<>();
    ArrayList<CommanDTO> sisterList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        SysApplication.context = getApplicationContext();
        if (instance == null) {
            instance = this;
        }
    }

    public ArrayList<CommanDTO> getHeightList() {
        if (heightList.size() > 0) {
            return heightList;

        } else {

            heightList.add(new CommanDTO("1", "4' 0\" (1.22 mts)"));
            heightList.add(new CommanDTO("2", "4' 1\" (1.24 mts)"));
            heightList.add(new CommanDTO("3", "4' 2\" (1.28 mts)"));
            heightList.add(new CommanDTO("4", "4' 3\" (1.31 mts)"));
            heightList.add(new CommanDTO("5", "4' 4\" (1.34 mts)"));
            heightList.add(new CommanDTO("6", "4' 5\" (1.35 mts)"));
            heightList.add(new CommanDTO("7", "4' 6\" (1.37 mts)"));
            heightList.add(new CommanDTO("8", "4' 7\" (1.40 mts)"));
            heightList.add(new CommanDTO("9", "4' 8\" (1.42 mts)"));
            heightList.add(new CommanDTO("10", "4' 9\" (1.45 mts)"));
            heightList.add(new CommanDTO("11", "4' 10\" (1.47 mts)"));
            heightList.add(new CommanDTO("12", "4' 11\" (1.50 mts)"));
            heightList.add(new CommanDTO("13", "5' 0\" (1.52 mts)"));
            heightList.add(new CommanDTO("14", "5' 1\" (1.55 mts)"));
            heightList.add(new CommanDTO("15", "5' 2\" (1.58 mts)"));
            heightList.add(new CommanDTO("16", "5' 3\" (1.60 mts)"));
            heightList.add(new CommanDTO("17", "5' 4\" (1.63 mts)"));
            heightList.add(new CommanDTO("18", "5' 5\" (1.65 mts)"));
            heightList.add(new CommanDTO("19", "5' 6\" (1.68 mts)"));
            heightList.add(new CommanDTO("20", "5' 7\" (1.70 mts)"));
            heightList.add(new CommanDTO("21", "5' 8\" (1.73 mts)"));
            heightList.add(new CommanDTO("22", "5' 9\" (1.75 mts)"));
            heightList.add(new CommanDTO("23", "5' 10\" (1.78 mts)"));
            heightList.add(new CommanDTO("24", "5' 11\" (1.80 mts)"));
            heightList.add(new CommanDTO("25", "6' 0\" (1.83 mts)"));
            heightList.add(new CommanDTO("26", "6' 1\" (1.85 mts)"));
            heightList.add(new CommanDTO("27", "6' 2\" (1.88 mts)"));
            heightList.add(new CommanDTO("28", "6' 3\" (1.91 mts)"));
            heightList.add(new CommanDTO("29", "6' 4\" (1.93 mts)"));
            heightList.add(new CommanDTO("30", "6' 5\" (1.96 mts)"));
            heightList.add(new CommanDTO("31", "6' 6\" (1.98 mts)"));
            heightList.add(new CommanDTO("32", "6' 7\" (2.01 mts)"));
            heightList.add(new CommanDTO("33", "6' 8\" (2.03 mts)"));
            heightList.add(new CommanDTO("34", "6' 9\" (2.06 mts)"));
            heightList.add(new CommanDTO("35", "6' 10\" (2.08 mts)"));
            heightList.add(new CommanDTO("36", "6' 11\" (2.11 mts)"));
            heightList.add(new CommanDTO("37", "7' (2.13 mts) plus"));
            return heightList;

        }

    }

    public ArrayList<CommanDTO> getIncomeList() {
        if (incomeList.size() > 0) {
            return incomeList;

        } else {
            incomeList.add(new CommanDTO("1", "Rs. No Income"));
            incomeList.add(new CommanDTO("2", "Rs.0 - 1 Lakh"));
            incomeList.add(new CommanDTO("3", "Rs.1 - 2 Lakh"));
            incomeList.add(new CommanDTO("4", "Rs.2 - 3 Lakh"));
            incomeList.add(new CommanDTO("5", "Rs.3 - 4 Lakh"));
            incomeList.add(new CommanDTO("6", "Rs.4 - 5Lakh"));
            incomeList.add(new CommanDTO("7", "Rs.5 - 8 Lakh"));
            incomeList.add(new CommanDTO("8", "Rs.8 - 10 Lakh"));
            incomeList.add(new CommanDTO("9", "Rs.10 - 15 Lakh"));
            incomeList.add(new CommanDTO("10", "Rs.15 - 20 Lakh"));
            incomeList.add(new CommanDTO("11", "Rs.20 - 35 Lakh"));
            incomeList.add(new CommanDTO("12", "Rs.35 - 50 Lakh"));
            incomeList.add(new CommanDTO("13", "Rs.50 Lakh & above"));

            return incomeList;
        }
    }

    public ArrayList<CommanDTO> getBloodList() {
        if (bloodList.size() > 0) {
            return bloodList;

        } else {
            bloodList.add(new CommanDTO("1", "Don't Know"));
            bloodList.add(new CommanDTO("2", "A+"));
            bloodList.add(new CommanDTO("3", "A-"));
            bloodList.add(new CommanDTO("4", "B+"));
            bloodList.add(new CommanDTO("5", "B-"));
            bloodList.add(new CommanDTO("6", "AB+"));
            bloodList.add(new CommanDTO("7", "AB-"));
            bloodList.add(new CommanDTO("8", "O+"));
            bloodList.add(new CommanDTO("9", "O-"));

            return bloodList;
        }
    }

    public ArrayList<CommanDTO> getMaritalList() {

        if (maritalList.size() > 0) {
            return maritalList;

        } else {
            maritalList.add(new CommanDTO("1", "Never Married"));
            maritalList.add(new CommanDTO("2", "Second Marriage"));

            return maritalList;
        }
    }

    public ArrayList<CommanDTO> getBodyType() {

        if (bodytypelist.size() > 0) {
            return bodytypelist;

        } else {
            bodytypelist.add(new CommanDTO("1", "Slim"));
            bodytypelist.add(new CommanDTO("2", "Average"));
            bodytypelist.add(new CommanDTO("3", "Athletic"));
            bodytypelist.add(new CommanDTO("4", "Heavy"));
            return bodytypelist;
        }
    }

    public ArrayList<CommanDTO> getComplexion() {
        if (complexionlist.size() > 0) {
            return complexionlist;

        } else {
            complexionlist.add(new CommanDTO("1", "Very Fair"));
            complexionlist.add(new CommanDTO("2", "Fair"));
            complexionlist.add(new CommanDTO("3", "Wheatish"));
            complexionlist.add(new CommanDTO("4", "Wheatish Brown"));
            complexionlist.add(new CommanDTO("5", "Dark"));
            return complexionlist;
        }
    }

    public ArrayList<CommanDTO> getChallenged() {
        if (challengedlist.size() > 0) {
            return challengedlist;
        } else {
            challengedlist.add(new CommanDTO("1", "None"));
            challengedlist.add(new CommanDTO("2", "Physically Handicapped form birth"));
            challengedlist.add(new CommanDTO("3", "Physically Handicappe due to accident"));
            challengedlist.add(new CommanDTO("4", "Mentally Challenged form birth"));
            challengedlist.add(new CommanDTO("5", "Mentally Challenged due to accident"));

            return challengedlist;
        }
    }


    public ArrayList<CommanDTO> getManglikList() {
        if (manglikList.size() > 0) {
            return manglikList;
        } else {
            manglikList.add(new CommanDTO("1", "Manglik"));
            manglikList.add(new CommanDTO("2", "Non Manglik"));
            manglikList.add(new CommanDTO("3", "Angshik (Partial Manglik)"));
            return manglikList;
        }
    }

    public ArrayList<CommanDTO> getFamilyStatus() {
        if (Famailystatuslist.size() > 0) {
            return Famailystatuslist;
        } else {
            Famailystatuslist.add(new CommanDTO("1", "Rich/Affluent"));
            Famailystatuslist.add(new CommanDTO("2", "Upper Middle Class"));
            Famailystatuslist.add(new CommanDTO("3", "Middle Class"));
            return Famailystatuslist;
        }
    }

    public ArrayList<CommanDTO> getFamilyType() {
        if (Familytypelist.size() > 0) {
            return Familytypelist;
        } else {
            Familytypelist.add(new CommanDTO("1", "Joint Family"));
            Familytypelist.add(new CommanDTO("2", "Nuclear Family"));
            Familytypelist.add(new CommanDTO("3", "Other"));
            return Familytypelist;
        }
    }

    public ArrayList<CommanDTO> getFamilyValues() {
        if (Familyvalueslist.size() > 0) {
            return Familyvalueslist;
        } else {
            Familyvalueslist.add(new CommanDTO("1", "Orthodox"));
            Familyvalueslist.add(new CommanDTO("2", "Conservative"));
            Familyvalueslist.add(new CommanDTO("3", "Moderate"));
            Familyvalueslist.add(new CommanDTO("4", "Liberal"));
            return Familyvalueslist;
        }
    }

    public ArrayList<CommanDTO> getOccupationList() {
        if (occupationList.size() > 0) {
            return occupationList;
        } else {
            occupationList.add(new CommanDTO("1", "Looking for a job"));
            occupationList.add(new CommanDTO("2", "Not working"));
            occupationList.add(new CommanDTO("3", "Actor/Model"));
            occupationList.add(new CommanDTO("4", "Advertising Professional"));
            occupationList.add(new CommanDTO("5", "Agent"));
            occupationList.add(new CommanDTO("6", "Air Hostess"));
            occupationList.add(new CommanDTO("7", "Analyst"));
            occupationList.add(new CommanDTO("8", "Architect"));
            occupationList.add(new CommanDTO("9", "BPO/ITeS"));
            occupationList.add(new CommanDTO("10", "Banking Professional"));
            occupationList.add(new CommanDTO("11", "Beautician"));
            occupationList.add(new CommanDTO("12", "Businessperson"));
            occupationList.add(new CommanDTO("13", "Chartered accountant"));
            occupationList.add(new CommanDTO("14", "Civil Services (IAS/ IFS/ IPS/ IRS)"));
            occupationList.add(new CommanDTO("15", "Consultant"));
            occupationList.add(new CommanDTO("16", "Corporate Communication"));
            occupationList.add(new CommanDTO("17", "Corporate Planning Professional"));
            occupationList.add(new CommanDTO("18", "Customer Services"));
            occupationList.add(new CommanDTO("19", "Cyber / Network Security"));
            occupationList.add(new CommanDTO("20", "Defence"));
            occupationList.add(new CommanDTO("21", "Doctor"));
            occupationList.add(new CommanDTO("22", "Education Professional"));
            occupationList.add(new CommanDTO("23", "Engineer - Non IT"));
            occupationList.add(new CommanDTO("24", "Farming"));
            occupationList.add(new CommanDTO("25", "Fashion Designer"));
            occupationList.add(new CommanDTO("26", "Film Professional"));
            occupationList.add(new CommanDTO("27", "Financial Services/Accounting"));
            occupationList.add(new CommanDTO("28", "Fitness Professional"));
            occupationList.add(new CommanDTO("29", "Govt. Services"));
            occupationList.add(new CommanDTO("30", "HR Professional"));
            occupationList.add(new CommanDTO("31", "Hardware/Telecom"));
            occupationList.add(new CommanDTO("32", "Healthcare Professional"));
            occupationList.add(new CommanDTO("33", "Hotels/Hospitality Professional"));
            occupationList.add(new CommanDTO("34", "Interior Designer"));
            occupationList.add(new CommanDTO("35", "Journalist"));
            occupationList.add(new CommanDTO("36", "Lawyer/Legal Professional"));
            occupationList.add(new CommanDTO("37", "Logistics/SCM Professional"));
            occupationList.add(new CommanDTO("38", "Manager"));
            occupationList.add(new CommanDTO("39", "Marketing Professional"));
            occupationList.add(new CommanDTO("40", "Media Professional"));
            occupationList.add(new CommanDTO("41", "Merchant Navy"));
            occupationList.add(new CommanDTO("42", "NGO/Social Services"));
            occupationList.add(new CommanDTO("43", "Nurse"));
            occupationList.add(new CommanDTO("44", "Office Admin"));
            occupationList.add(new CommanDTO("45", "Operator/Technician"));
            occupationList.add(new CommanDTO("46", "Physiotherapist"));
            occupationList.add(new CommanDTO("47", "Pilot"));
            occupationList.add(new CommanDTO("48", "Police"));
            occupationList.add(new CommanDTO("49", "Private Security"));
            occupationList.add(new CommanDTO("50", "Product manager"));
            occupationList.add(new CommanDTO("51", "Professor/Lecturer"));
            occupationList.add(new CommanDTO("52", "Program Manager"));
            occupationList.add(new CommanDTO("53", "Project Manager - IT"));
            occupationList.add(new CommanDTO("54", "Project Manager - Non IT"));
            occupationList.add(new CommanDTO("55", "Psychologist"));
            occupationList.add(new CommanDTO("56", "Research Professional"));
            occupationList.add(new CommanDTO("57", "Sales Professional"));
            occupationList.add(new CommanDTO("58", "Scientist"));
            occupationList.add(new CommanDTO("59", "Secretary/Front Office"));
            occupationList.add(new CommanDTO("60", "Security Professional"));
            occupationList.add(new CommanDTO("61", "Self Employed"));
            occupationList.add(new CommanDTO("62", "Software Professional"));
            occupationList.add(new CommanDTO("63", "Sportsperson"));
            occupationList.add(new CommanDTO("64", "Student"));
            occupationList.add(new CommanDTO("65", "Teacher"));
            occupationList.add(new CommanDTO("66", "Top Management (CXO, M.D. etc.)"));
            occupationList.add(new CommanDTO("67", "UI/UX designer"));
            occupationList.add(new CommanDTO("68", "Web/Graphic Design"));
            occupationList.add(new CommanDTO("69", "Others"));
            return occupationList;
        }
    }


    public ArrayList<CommanDTO> getMotherOccupationList() {
        if (motheroccupationList.size() > 0) {
            return motheroccupationList;
        } else {
            motheroccupationList.add(new CommanDTO("1", "Housewife"));
            motheroccupationList.add(new CommanDTO("2", "Business/Entrepreneur"));
            motheroccupationList.add(new CommanDTO("3", "Service-Private"));
            motheroccupationList.add(new CommanDTO("4", "Service-Govt/PSU"));
            motheroccupationList.add(new CommanDTO("5", "Army/Armed forces"));
            motheroccupationList.add(new CommanDTO("6", "Civil Services"));
            motheroccupationList.add(new CommanDTO("7", "Teacher"));
            motheroccupationList.add(new CommanDTO("8", "Retired"));
            motheroccupationList.add(new CommanDTO("9", "Expired"));
            return motheroccupationList;
        }
    }

    public ArrayList<CommanDTO> getFatherOccupationList() {
        if (fatheroccupationList.size() > 0) {
            return fatheroccupationList;
        } else {
            fatheroccupationList.add(new CommanDTO("1", "Business/Entrepreneur"));
            fatheroccupationList.add(new CommanDTO("2", "Service - Private"));
            fatheroccupationList.add(new CommanDTO("3", "Service - Govt./PSU"));
            fatheroccupationList.add(new CommanDTO("4", "Army/Armed Forces"));
            fatheroccupationList.add(new CommanDTO("5", "Civil Services"));
            fatheroccupationList.add(new CommanDTO("6", "Retired"));
            fatheroccupationList.add(new CommanDTO("7", "Not Employed"));
            fatheroccupationList.add(new CommanDTO("8", "Expired"));
            return fatheroccupationList;
        }
    }

    public ArrayList<CommanDTO> getHobbiesList() {
        if (hobbiesList.size() > 0) {
            return hobbiesList;

        } else {
            hobbiesList.add(new CommanDTO("1", "Collecting Stamps"));
            hobbiesList.add(new CommanDTO("2", "Collecting Coins"));
            hobbiesList.add(new CommanDTO("3", "Collecting antiques"));
            hobbiesList.add(new CommanDTO("4", "Art / Handicraft"));
            hobbiesList.add(new CommanDTO("5", "Painting"));
            hobbiesList.add(new CommanDTO("6", "Cooking"));
            hobbiesList.add(new CommanDTO("7", "Photography"));
            hobbiesList.add(new CommanDTO("8", "Film-making"));
            hobbiesList.add(new CommanDTO("9", "Model building"));
            hobbiesList.add(new CommanDTO("10", "Gardening / Landscaping"));
            hobbiesList.add(new CommanDTO("11", "Fishing"));
            hobbiesList.add(new CommanDTO("12", "Bird watching"));
            hobbiesList.add(new CommanDTO("13", "Taking care of pets"));
            hobbiesList.add(new CommanDTO("14", "Playing musical instruments"));
            hobbiesList.add(new CommanDTO("15", "Singing"));
            hobbiesList.add(new CommanDTO("16", "Dancing"));
            hobbiesList.add(new CommanDTO("17", "Acting"));
            hobbiesList.add(new CommanDTO("18", "Ham radio"));
            hobbiesList.add(new CommanDTO("19", "Astrology / Palmistry / Numerology"));
            hobbiesList.add(new CommanDTO("20", "Graphology"));
            hobbiesList.add(new CommanDTO("21", "Solving Crosswords, Puzzles"));
            return hobbiesList;

        }


    }

    public ArrayList<CommanDTO> getInterestsList() {
        if (interestsList.size() > 0) {
            return interestsList;
        } else {
            interestsList.add(new CommanDTO("1", "Writing"));
            interestsList.add(new CommanDTO("2", "Reading / Book clubs"));
            interestsList.add(new CommanDTO("3", "Learning new languages"));
            interestsList.add(new CommanDTO("4", "Listening to music"));
            interestsList.add(new CommanDTO("5", "Movies"));
            interestsList.add(new CommanDTO("6", "Theatre"));
            interestsList.add(new CommanDTO("7", "Watching television"));
            interestsList.add(new CommanDTO("8", "Travel / Sightseeing"));
            interestsList.add(new CommanDTO("9", "Sports - Outdoor"));
            interestsList.add(new CommanDTO("10", "Sports - Indoor"));
            interestsList.add(new CommanDTO("11", "Trekking / Adventure sports"));
            interestsList.add(new CommanDTO("12", "Video / Computer games"));
            interestsList.add(new CommanDTO("13", "Health &amp; Fitness"));
            interestsList.add(new CommanDTO("14", "Yoga / Meditation"));
            interestsList.add(new CommanDTO("15", "Alternative healing"));
            interestsList.add(new CommanDTO("16", "Volunteering / Social Service"));
            interestsList.add(new CommanDTO("17", "Politics"));
            interestsList.add(new CommanDTO("18", "Net surfing"));
            interestsList.add(new CommanDTO("19", "Blogging"));
            return interestsList;
        }
    }

    public ArrayList<CommanDTO> getDietary() {
        if (dietaryList.size() > 0) {
            return dietaryList;
        } else {
            dietaryList.add(new CommanDTO("1", "Vegetarian"));
            dietaryList.add(new CommanDTO("2", "Non Vegetarian"));
            dietaryList.add(new CommanDTO("3", "Eggetarian"));
            return dietaryList;
        }
    }

    public ArrayList<CommanDTO> getHabitsDrink() {
        if (drinkingList.size() > 0) {
            return drinkingList;
        } else {
            drinkingList.add(new CommanDTO("1", "Yes"));
            drinkingList.add(new CommanDTO("2", "No"));
            drinkingList.add(new CommanDTO("3", "Occasionally"));
            return drinkingList;
        }
    }

    public ArrayList<CommanDTO> getHabitsSmok() {
        if (smokingList.size() > 0) {
            return smokingList;
        } else {
            smokingList.add(new CommanDTO("1", "Yes"));
            smokingList.add(new CommanDTO("2", "No"));
            smokingList.add(new CommanDTO("3", "Occasionally"));
            return smokingList;
        }
    }

    public ArrayList<CommanDTO> getLanguage() {
        if (lanuageList.size() > 0) {
            return lanuageList;
        } else {
            lanuageList.add(new CommanDTO("1", "Hindi"));
            lanuageList.add(new CommanDTO("2", "English"));
            return lanuageList;
        }
    }

    public ArrayList<CommanDTO> getBrother() {
        if (brotherList.size() > 0) {
            return brotherList;
        } else {
            brotherList.add(new CommanDTO("1", "None"));
            brotherList.add(new CommanDTO("2", "1"));
            brotherList.add(new CommanDTO("3", "2"));
            brotherList.add(new CommanDTO("4", "3"));
            brotherList.add(new CommanDTO("5", "3+"));
            return brotherList;
        }
    }

    public ArrayList<CommanDTO> getSister() {
        if (sisterList.size() > 0) {
            return sisterList;
        } else {
            sisterList.add(new CommanDTO("1", "None"));
            sisterList.add(new CommanDTO("2", "1"));
            sisterList.add(new CommanDTO("3", "2"));
            sisterList.add(new CommanDTO("4", "3"));
            sisterList.add(new CommanDTO("5", "3+"));
            return sisterList;
        }
    }


}