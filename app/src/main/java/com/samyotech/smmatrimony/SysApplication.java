package com.samyotech.smmatrimony;

import android.content.Context;
import com.samyotech.smmatrimony.Models.CommanDTO;

import java.util.ArrayList;

public class SysApplication {
    private static Context mContext;
    private static SysApplication INSTANCE = null;

    // other instance variables can be here

    private SysApplication() {
    }

    public static synchronized SysApplication getInstance(Context context) {

        if (INSTANCE == null) {
            mContext = context;
            INSTANCE = new SysApplication();

        }
        return (INSTANCE);
    }
    // other instance methods can follow


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
            maritalList.add(new CommanDTO("1", mContext.getString(R.string.nevermarried)));
            maritalList.add(new CommanDTO("2", mContext.getString(R.string.secondmarriage)));

            return maritalList;
        }
    }

    public ArrayList<CommanDTO> getBodyType() {

        if (bodytypelist.size() > 0) {
            return bodytypelist;

        } else {
            bodytypelist.add(new CommanDTO("1", mContext.getString(R.string.slim)));
            bodytypelist.add(new CommanDTO("2", mContext.getString(R.string.average)));
            bodytypelist.add(new CommanDTO("3", mContext.getString(R.string.athletic)));
            bodytypelist.add(new CommanDTO("4", mContext.getString(R.string.heavy)));
            return bodytypelist;
        }
    }

    public ArrayList<CommanDTO> getComplexion() {
        if (complexionlist.size() > 0) {
            return complexionlist;

        } else {
            complexionlist.add(new CommanDTO("1", mContext.getString(R.string.veryfair)));
            complexionlist.add(new CommanDTO("2", mContext.getString(R.string.fair)));
            complexionlist.add(new CommanDTO("3", mContext.getString(R.string.wheatish)));
            complexionlist.add(new CommanDTO("4", mContext.getString(R.string.wheatishbrown)));
            complexionlist.add(new CommanDTO("5", mContext.getString(R.string.dark)));
            return complexionlist;
        }
    }

    public ArrayList<CommanDTO> getChallenged() {
        if (challengedlist.size() > 0) {
            return challengedlist;
        } else {
            challengedlist.add(new CommanDTO("1", mContext.getString(R.string.none)));
            challengedlist.add(new CommanDTO("2", mContext.getString(R.string.challangedlist1)));
            challengedlist.add(new CommanDTO("3", mContext.getString(R.string.challangedlist2)));
            challengedlist.add(new CommanDTO("4", mContext.getString(R.string.challangedlist3)));
            challengedlist.add(new CommanDTO("5", mContext.getString(R.string.challangedlist4)));

            return challengedlist;
        }
    }


    public ArrayList<CommanDTO> getManglikList() {
        if (manglikList.size() > 0) {
            return manglikList;
        } else {
            manglikList.add(new CommanDTO("1", mContext.getString(R.string.manglikList1)));
            manglikList.add(new CommanDTO("2", mContext.getString(R.string.manglikLis2)));
            manglikList.add(new CommanDTO("3", mContext.getString(R.string.manglikList3)));
            return manglikList;
        }
    }

    public ArrayList<CommanDTO> getFamilyStatus() {
        if (Famailystatuslist.size() > 0) {
            return Famailystatuslist;
        } else {
            Famailystatuslist.add(new CommanDTO("1", mContext.getString(R.string.Famailystatuslist1)));
            Famailystatuslist.add(new CommanDTO("2", mContext.getString(R.string.Famailystatuslist2)));
            Famailystatuslist.add(new CommanDTO("3", mContext.getString(R.string.Famailystatuslist3)));
            return Famailystatuslist;
        }
    }

    public ArrayList<CommanDTO> getFamilyType() {
        if (Familytypelist.size() > 0) {
            return Familytypelist;
        } else {
            Familytypelist.add(new CommanDTO("1", mContext.getString(R.string.Familytypelist1)));
            Familytypelist.add(new CommanDTO("2", mContext.getString(R.string.Familytypelist)));
            Familytypelist.add(new CommanDTO("3", mContext.getString(R.string.other)));
            return Familytypelist;
        }
    }

    public ArrayList<CommanDTO> getFamilyValues() {
        if (Familyvalueslist.size() > 0) {
            return Familyvalueslist;
        } else {
            Familyvalueslist.add(new CommanDTO("1", mContext.getString(R.string.orthodox)));
            Familyvalueslist.add(new CommanDTO("2", mContext.getString(R.string.conservative)));
            Familyvalueslist.add(new CommanDTO("3", mContext.getString(R.string.moderate)));
            Familyvalueslist.add(new CommanDTO("4", mContext.getString(R.string.liberal)));
            return Familyvalueslist;
        }
    }

    public ArrayList<CommanDTO> getOccupationList() {
        if (occupationList.size() > 0) {
            return occupationList;
        } else {
            occupationList.add(new CommanDTO("1", mContext.getString(R.string.occupationList1)));
            occupationList.add(new CommanDTO("2", mContext.getString(R.string.occupationList2)));
            occupationList.add(new CommanDTO("3", mContext.getString(R.string.occupationList3)));
            occupationList.add(new CommanDTO("4", mContext.getString(R.string.occupationList4)));
            occupationList.add(new CommanDTO("5", mContext.getString(R.string.occupationList5)));
            occupationList.add(new CommanDTO("6", mContext.getString(R.string.occupationList6)));
            occupationList.add(new CommanDTO("7", mContext.getString(R.string.occupationList7)));
            occupationList.add(new CommanDTO("8", mContext.getString(R.string.occupationList8)));
            occupationList.add(new CommanDTO("9", mContext.getString(R.string.occupationList9)));
            occupationList.add(new CommanDTO("10", mContext.getString(R.string.occupationList10)));
            occupationList.add(new CommanDTO("11", mContext.getString(R.string.occupationList11)));
            occupationList.add(new CommanDTO("12", mContext.getString(R.string.occupationList12)));
            occupationList.add(new CommanDTO("13", mContext.getString(R.string.occupationList13)));
            occupationList.add(new CommanDTO("14", mContext.getString(R.string.occupationList14)));
            occupationList.add(new CommanDTO("15", mContext.getString(R.string.occupationList15)));
            occupationList.add(new CommanDTO("16", mContext.getString(R.string.occupationList16)));
            occupationList.add(new CommanDTO("17", mContext.getString(R.string.occupationList17)));
            occupationList.add(new CommanDTO("18", mContext.getString(R.string.occupationList18)));
            occupationList.add(new CommanDTO("19", mContext.getString(R.string.occupationList19)));
            occupationList.add(new CommanDTO("20", mContext.getString(R.string.occupationList20)));
            occupationList.add(new CommanDTO("21", mContext.getString(R.string.occupationList21)));
            occupationList.add(new CommanDTO("22", mContext.getString(R.string.occupationList22)));
            occupationList.add(new CommanDTO("23", mContext.getString(R.string.occupationList23)));
            occupationList.add(new CommanDTO("24", mContext.getString(R.string.occupationList24)));
            occupationList.add(new CommanDTO("25", mContext.getString(R.string.occupationList25)));
            occupationList.add(new CommanDTO("26", mContext.getString(R.string.occupationList26)));
            occupationList.add(new CommanDTO("27", mContext.getString(R.string.occupationList27)));
            occupationList.add(new CommanDTO("28", mContext.getString(R.string.occupationList28)));
            occupationList.add(new CommanDTO("29", mContext.getString(R.string.occupationList29)));
            occupationList.add(new CommanDTO("30", mContext.getString(R.string.occupationList30)));
            occupationList.add(new CommanDTO("31", mContext.getString(R.string.occupationList31)));
            occupationList.add(new CommanDTO("32", mContext.getString(R.string.occupationList32)));
            occupationList.add(new CommanDTO("33", mContext.getString(R.string.occupationList33)));
            occupationList.add(new CommanDTO("34", mContext.getString(R.string.occupationList34)));
            occupationList.add(new CommanDTO("35", mContext.getString(R.string.occupationList35)));
            occupationList.add(new CommanDTO("36", mContext.getString(R.string.occupationList36)));
            occupationList.add(new CommanDTO("37", mContext.getString(R.string.occupationList37)));
            occupationList.add(new CommanDTO("38", mContext.getString(R.string.occupationList38)));
            occupationList.add(new CommanDTO("39", mContext.getString(R.string.occupationList39)));
            occupationList.add(new CommanDTO("40", mContext.getString(R.string.occupationList40)));
            occupationList.add(new CommanDTO("41", mContext.getString(R.string.occupationList41)));
            occupationList.add(new CommanDTO("42", mContext.getString(R.string.occupationList42)));
            occupationList.add(new CommanDTO("43", mContext.getString(R.string.occupationList43)));
            occupationList.add(new CommanDTO("44", mContext.getString(R.string.occupationList44)));
            occupationList.add(new CommanDTO("45", mContext.getString(R.string.occupationList45)));
            occupationList.add(new CommanDTO("46", mContext.getString(R.string.occupationList46)));
            occupationList.add(new CommanDTO("47", mContext.getString(R.string.occupationList47)));
            occupationList.add(new CommanDTO("48", mContext.getString(R.string.occupationList48)));
            occupationList.add(new CommanDTO("49", mContext.getString(R.string.occupationList49)));
            occupationList.add(new CommanDTO("50", mContext.getString(R.string.occupationList50)));
            occupationList.add(new CommanDTO("51", mContext.getString(R.string.occupationList51)));
            occupationList.add(new CommanDTO("52", mContext.getString(R.string.occupationList52)));
            occupationList.add(new CommanDTO("53", mContext.getString(R.string.occupationList53)));
            occupationList.add(new CommanDTO("54", mContext.getString(R.string.occupationList54)));
            occupationList.add(new CommanDTO("55", mContext.getString(R.string.occupationList55)));
            occupationList.add(new CommanDTO("56", mContext.getString(R.string.occupationList56)));
            occupationList.add(new CommanDTO("57", mContext.getString(R.string.occupationList57)));
            occupationList.add(new CommanDTO("58", mContext.getString(R.string.occupationList58)));
            occupationList.add(new CommanDTO("59", mContext.getString(R.string.occupationList59)));
            occupationList.add(new CommanDTO("60", mContext.getString(R.string.occupationList60)));
            occupationList.add(new CommanDTO("61", mContext.getString(R.string.occupationList61)));
            occupationList.add(new CommanDTO("62", mContext.getString(R.string.occupationList62)));
            occupationList.add(new CommanDTO("63", mContext.getString(R.string.occupationList63)));
            occupationList.add(new CommanDTO("64", mContext.getString(R.string.occupationList64)));
            occupationList.add(new CommanDTO("65", mContext.getString(R.string.occupationList65)));
            occupationList.add(new CommanDTO("66", mContext.getString(R.string.occupationList66)));
            occupationList.add(new CommanDTO("67", mContext.getString(R.string.occupationList67)));
            occupationList.add(new CommanDTO("68", mContext.getString(R.string.occupationList68)));
            occupationList.add(new CommanDTO("69", mContext.getString(R.string.occupationList69)));
            return occupationList;
        }
    }


    public ArrayList<CommanDTO> getMotherOccupationList() {
        if (motheroccupationList.size() > 0) {
            return motheroccupationList;
        } else {
            motheroccupationList.add(new CommanDTO("1", mContext.getString(R.string.motheroccupationList1)));
            motheroccupationList.add(new CommanDTO("2", mContext.getString(R.string.motheroccupationList2)));
            motheroccupationList.add(new CommanDTO("3", mContext.getString(R.string.motheroccupationList3)));
            motheroccupationList.add(new CommanDTO("4", mContext.getString(R.string.motheroccupationList4)));
            motheroccupationList.add(new CommanDTO("5", mContext.getString(R.string.motheroccupationList5)));
            motheroccupationList.add(new CommanDTO("6", mContext.getString(R.string.motheroccupationList6)));
            motheroccupationList.add(new CommanDTO("7", mContext.getString(R.string.motheroccupationList7)));
            motheroccupationList.add(new CommanDTO("8", mContext.getString(R.string.motheroccupationList8)));
            motheroccupationList.add(new CommanDTO("9", mContext.getString(R.string.motheroccupationList9)));
            return motheroccupationList;
        }
    }

    public ArrayList<CommanDTO> getFatherOccupationList() {
        if (fatheroccupationList.size() > 0) {
            return fatheroccupationList;
        } else {
            fatheroccupationList.add(new CommanDTO("1", mContext.getString(R.string.fatheroccupationList1)));
            fatheroccupationList.add(new CommanDTO("2", mContext.getString(R.string.fatheroccupationList2)));
            fatheroccupationList.add(new CommanDTO("3", mContext.getString(R.string.fatheroccupationList3)));
            fatheroccupationList.add(new CommanDTO("4", mContext.getString(R.string.fatheroccupationList4)));
            fatheroccupationList.add(new CommanDTO("5", mContext.getString(R.string.fatheroccupationList5)));
            fatheroccupationList.add(new CommanDTO("6", mContext.getString(R.string.fatheroccupationList6)));
            fatheroccupationList.add(new CommanDTO("7", mContext.getString(R.string.fatheroccupationList7)));
            fatheroccupationList.add(new CommanDTO("8", mContext.getString(R.string.fatheroccupationList8)));
            return fatheroccupationList;
        }
    }

    public ArrayList<CommanDTO> getHobbiesList() {
        if (hobbiesList.size() > 0) {
            return hobbiesList;

        } else {
            hobbiesList.add(new CommanDTO("1", mContext.getString(R.string.hobbiesList1)));
            hobbiesList.add(new CommanDTO("2", mContext.getString(R.string.hobbiesList2)));
            hobbiesList.add(new CommanDTO("3", mContext.getString(R.string.hobbiesList3)));
            hobbiesList.add(new CommanDTO("4", mContext.getString(R.string.hobbiesList4)));
            hobbiesList.add(new CommanDTO("5", mContext.getString(R.string.hobbiesList5)));
            hobbiesList.add(new CommanDTO("6", mContext.getString(R.string.hobbiesList6)));
            hobbiesList.add(new CommanDTO("7", mContext.getString(R.string.hobbiesList7)));
            hobbiesList.add(new CommanDTO("8", mContext.getString(R.string.hobbiesList8)));
            hobbiesList.add(new CommanDTO("9", mContext.getString(R.string.hobbiesList9)));
            hobbiesList.add(new CommanDTO("10", mContext.getString(R.string.hobbiesList10)));
            hobbiesList.add(new CommanDTO("11", mContext.getString(R.string.hobbiesList11)));
            hobbiesList.add(new CommanDTO("12", mContext.getString(R.string.hobbiesList12)));
            hobbiesList.add(new CommanDTO("13", mContext.getString(R.string.hobbiesList13)));
            hobbiesList.add(new CommanDTO("14", mContext.getString(R.string.hobbiesList14)));
            hobbiesList.add(new CommanDTO("15", mContext.getString(R.string.hobbiesList15)));
            hobbiesList.add(new CommanDTO("16", mContext.getString(R.string.hobbiesList16)));
            hobbiesList.add(new CommanDTO("17", mContext.getString(R.string.hobbiesList17)));
            hobbiesList.add(new CommanDTO("18", mContext.getString(R.string.hobbiesList18)));
            hobbiesList.add(new CommanDTO("19", mContext.getString(R.string.hobbiesList19)));
            hobbiesList.add(new CommanDTO("20", mContext.getString(R.string.hobbiesList20)));
            hobbiesList.add(new CommanDTO("21", mContext.getString(R.string.hobbiesList21)));
            return hobbiesList;

        }


    }

    public ArrayList<CommanDTO> getInterestsList() {
        if (interestsList.size() > 0) {
            return interestsList;
        } else {
            interestsList.add(new CommanDTO("1", mContext.getString(R.string.interestsList1)));
            interestsList.add(new CommanDTO("2", mContext.getString(R.string.interestsList2)));
            interestsList.add(new CommanDTO("3", mContext.getString(R.string.interestsList3)));
            interestsList.add(new CommanDTO("4", mContext.getString(R.string.interestsList4)));
            interestsList.add(new CommanDTO("5", mContext.getString(R.string.interestsList5)));
            interestsList.add(new CommanDTO("6", mContext.getString(R.string.interestsList6)));
            interestsList.add(new CommanDTO("7", mContext.getString(R.string.interestsList7)));
            interestsList.add(new CommanDTO("8", mContext.getString(R.string.interestsList8)));
            interestsList.add(new CommanDTO("9", mContext.getString(R.string.interestsList9)));
            interestsList.add(new CommanDTO("10", mContext.getString(R.string.interestsList10)));
            interestsList.add(new CommanDTO("11", mContext.getString(R.string.interestsList11)));
            interestsList.add(new CommanDTO("12", mContext.getString(R.string.interestsList12)));
            interestsList.add(new CommanDTO("13", mContext.getString(R.string.interestsList13)));
            interestsList.add(new CommanDTO("14", mContext.getString(R.string.interestsList14)));
            interestsList.add(new CommanDTO("15", mContext.getString(R.string.interestsList15)));
            interestsList.add(new CommanDTO("16", mContext.getString(R.string.interestsList16)));
            interestsList.add(new CommanDTO("17", mContext.getString(R.string.interestsList17)));
            interestsList.add(new CommanDTO("18", mContext.getString(R.string.interestsList18)));
            interestsList.add(new CommanDTO("19", mContext.getString(R.string.interestsList19)));
            return interestsList;
        }
    }

    public ArrayList<CommanDTO> getDietary() {
        if (dietaryList.size() > 0) {
            return dietaryList;
        } else {
            dietaryList.add(new CommanDTO("1", mContext.getString(R.string.vegetarian)));
            dietaryList.add(new CommanDTO("2", mContext.getString(R.string.nonvegetarian)));
            dietaryList.add(new CommanDTO("3", mContext.getString(R.string.eggetarian)));
            return dietaryList;
        }
    }

    public ArrayList<CommanDTO> getHabitsDrink() {
        if (drinkingList.size() > 0) {
            return drinkingList;
        } else {
            drinkingList.add(new CommanDTO("1", mContext.getString(R.string.yes)));
            drinkingList.add(new CommanDTO("2", mContext.getString(R.string.no)));
            drinkingList.add(new CommanDTO("3", mContext.getString(R.string.occasionally)));
            return drinkingList;
        }
    }

    public ArrayList<CommanDTO> getHabitsSmok() {
        if (smokingList.size() > 0) {
            return smokingList;
        } else {
            smokingList.add(new CommanDTO("1", mContext.getString(R.string.yes)));
            smokingList.add(new CommanDTO("2", mContext.getString(R.string.no)));
            smokingList.add(new CommanDTO("3", mContext.getString(R.string.occasionally)));
            return smokingList;
        }
    }

    public ArrayList<CommanDTO> getLanguage() {
        if (lanuageList.size() > 0) {
            return lanuageList;
        } else {
            lanuageList.add(new CommanDTO("1", mContext.getString(R.string.hindi)));
            lanuageList.add(new CommanDTO("2", mContext.getString(R.string.english)));
            return lanuageList;
        }
    }

    public ArrayList<CommanDTO> getBrother() {
        if (brotherList.size() > 0) {
            return brotherList;
        } else {
            brotherList.add(new CommanDTO("1", mContext.getString(R.string.none)));
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
            sisterList.add(new CommanDTO("1", mContext.getString(R.string.none)));
            sisterList.add(new CommanDTO("2", "1"));
            sisterList.add(new CommanDTO("3", "2"));
            sisterList.add(new CommanDTO("4", "3"));
            sisterList.add(new CommanDTO("5", "3+"));
            return sisterList;
        }
    }


}