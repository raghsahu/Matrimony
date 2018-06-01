package com.inspire.rkspmatrimony.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inspire.rkspmatrimony.Models.CommanDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.fragment.MatchesFrag;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.OnSpinerItemClick;
import com.inspire.rkspmatrimony.view.CustomTextView;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerDialog {
    ArrayList<CommanDTO> items;
    Activity context;
    String dTitle;
    String closeTitle = "Close";
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int clickpos;
    int style;
    Fragment fragment;

    private int limit = -1;
    private int selected = 0;
    private LimitExceedListener limitListener;
    int selectedIndex = -1;
    ListView listView;
    MyAdapterRadio myAdapterRadio;
    MyAdapterCheck myAdapterCheck;
    int pos;

    String name = "", image = "";

    public SpinnerDialog(Activity activity, ArrayList<CommanDTO> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<CommanDTO> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<CommanDTO> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public SpinnerDialog(Activity activity, ArrayList<CommanDTO> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
    }

    public SpinnerDialog(Activity activity, String name, String image, int style) {
        this.context = activity;
        this.name = name;
        this.image = image;
        this.style = style;
    }


    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void showConatactDialog() {
        Builder adb = new Builder(this.context);
        View v = this.context.getLayoutInflater().inflate(R.layout.dialog_layout_contact, (ViewGroup) null);
        CircleImageView ivImage = (CircleImageView) v.findViewById(R.id.ivImage);
        CustomTextViewBold tvName = (CustomTextViewBold) v.findViewById(R.id.tvName);
        CustomTextViewBold tvClose = (CustomTextViewBold) v.findViewById(R.id.tvClose);
        CustomTextViewBold tvOk = (CustomTextViewBold) v.findViewById(R.id.tvOk);

        adb.setView(v);
        tvName.setText(name);
        Glide.with(context).
                load(Consts.IMAGE_URL + image)
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImage);

        this.alertDialog = adb.create();
        this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.setCancelable(false);

        tvOk.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        tvClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        this.alertDialog.show();
    }

    public void showSpinerDialog() {
        Builder adb = new Builder(this.context);
        View v = this.context.getLayoutInflater().inflate(R.layout.dialog_layout_radio, (ViewGroup) null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(this.closeTitle);
        title.setText(this.dTitle);
        listView = (ListView) v.findViewById(R.id.list);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        myAdapterRadio = new MyAdapterRadio(this.context, this.items);
        listView.setAdapter(myAdapterRadio);
        adb.setView(v);


        this.alertDialog = adb.create();
        this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.setCancelable(false);

        searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                myAdapterRadio.getFilter().filter(searchBox.getText().toString());
            }
        });
        rippleViewClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView) view.findViewById(R.id.text1);
                String selectedID = "";
                for (int j = 0; j < items.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(items.get(j).toString())) {
                        pos = j;
                        selectedID = items.get(j).getId();
                    }
                    if (j == i) {
                        items.get(j).setSelected(true);
                    } else {
                        items.get(j).setSelected(false);
                    }
                }
                onSpinerItemClick.onClick(t.getText().toString(), selectedID, pos);
                alertDialog.dismiss();
            }
        });

/*
        Ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                StringBuilder allLabels = new StringBuilder();
                StringBuilder allID = new StringBuilder();

                for (CommanDTO s : myAdapterRadio.arrayList) {
                    if (s.isSelected()) {
                        allLabels.append(s);
                        allID.append(s.getId());


                    }

                }
                SpinnerDialog.this.onSpinerItemClick.onClick(allLabels.toString(), allID.toString(), clickpos);

                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
*/

        this.alertDialog.show();
    }


/*
    public void updateList(int pos) {
        for (int i = 0; i < this.items.size(); i++) {
            if (i == pos) {
                this.items.get(i).setSelected(true);
            } else {
                this.items.get(i).setSelected(false);
            }
        }
        myAdapterRadio.notifyDataSetChanged();
    }
*/

    public void showSpinerDialogMultiple() {
        Builder adb = new Builder(this.context);
        View v = this.context.getLayoutInflater().inflate(R.layout.dialog_layout_check, (ViewGroup) null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView Ok = (TextView) v.findViewById(R.id.Ok);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(this.closeTitle);
        title.setText(this.dTitle);
        listView = (ListView) v.findViewById(R.id.list);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        myAdapterCheck = new MyAdapterCheck(this.context, this.items);
        listView.setAdapter(myAdapterCheck);
        adb.setView(v);
        this.alertDialog = adb.create();
        this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.setCancelable(false);

        searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                myAdapterCheck.getFilter().filter(searchBox.getText().toString());
            }
        });
        rippleViewClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        Ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StringBuilder allLabels = new StringBuilder();
                StringBuilder allID = new StringBuilder();
                for (CommanDTO s : myAdapterCheck.arrayList) {
                    if (s.isSelected()) {
                        if (allLabels.length() > 0) {
                            allLabels.append(", "); // some divider between the different texts
                            allID.append(", "); // some divider between the different texts
                        }
                        allLabels.append(s);
                        allID.append(s.getId());

                    }

                }
                SpinnerDialog.this.onSpinerItemClick.onClick(allLabels.toString(), allID.toString(), clickpos);

                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        this.alertDialog.show();
    }


    public class MyAdapterCheck extends BaseAdapter implements Filterable {

        ArrayList<CommanDTO> arrayList;
        ArrayList<CommanDTO> mOriginalValues; // Original Values
        LayoutInflater inflater;

        public MyAdapterCheck(Context context, ArrayList<CommanDTO> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            CustomTextView text1;
            CheckBox checkBox1;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.spinner_view_checkbox, parent, false);
                holder.text1 = (CustomTextView) convertView.findViewById(R.id.text1);
                holder.checkBox1 = (CheckBox) convertView.findViewById(R.id.checkBox1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.text1.setText(arrayList.get(position).getName());
            holder.text1.setTypeface(null, Typeface.NORMAL);
            holder.checkBox1.setChecked(arrayList.get(position).isSelected());

            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (arrayList.get(position).isSelected()) { // deselect
                        selected--;
                    } else if (selected == limit) { // select with limit
                        if (limitListener != null)
                            limitListener.onLimitListener(arrayList.get(position));
                        return;
                    } else { // selected
                        selected++;
                    }

                    final ViewHolder temp = (ViewHolder) v.getTag();
                    temp.checkBox1.setChecked(!temp.checkBox1.isChecked());

                    arrayList.get(position).setSelected(!arrayList.get(position).isSelected());
                    Log.i("TAG", "On Click Selected Item : " + arrayList.get(position).getName() + " : " + arrayList.get(position).isSelected());
                    notifyDataSetChanged();
                }
            });
            holder.checkBox1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (arrayList.get(position).isSelected()) { // deselect
                        selected--;
                    } else if (selected == limit) { // select with limit
                        if (limitListener != null)
                            limitListener.onLimitListener(arrayList.get(position));
                        return;
                    } else { // selected
                        selected++;
                    }

                    final ViewHolder temp = (ViewHolder) v.getTag();
                    temp.checkBox1.setChecked(!temp.checkBox1.isChecked());

                    arrayList.get(position).setSelected(!arrayList.get(position).isSelected());
                    Log.i("TAG", "On Click Selected Item : " + arrayList.get(position).getName() + " : " + arrayList.get(position).isSelected());
                    notifyDataSetChanged();
                }
            });


            if (arrayList.get(position).isSelected()) {
//                holder.text1.setTypeface(null, Typeface.BOLD);
                // convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.list_selected));
            }
            holder.checkBox1.setTag(holder);

            return convertView;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public Filter getFilter() {
            return new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    arrayList = (ArrayList<CommanDTO>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<CommanDTO> FilteredArrList = new ArrayList<>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<>(arrayList); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getName();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(mOriginalValues.get(i));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
        }
    }

    public class MyAdapterRadio extends BaseAdapter implements Filterable {

        ArrayList<CommanDTO> arrayList;
        ArrayList<CommanDTO> mOriginalValues; // Original Values
        LayoutInflater inflater;


        public MyAdapterRadio(Context context, ArrayList<CommanDTO> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            public CustomTextView text1;
            public RadioButton radioBtn;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.spinner_view_radio, parent, false);
                holder.text1 = (CustomTextView) convertView.findViewById(R.id.text1);
                holder.radioBtn = (RadioButton) convertView.findViewById(R.id.radioBtn);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text1.setText(arrayList.get(position).getName());
            holder.text1.setTypeface(null, Typeface.NORMAL);

            if (arrayList.get(position).isSelected()) {
                holder.radioBtn.setChecked(true);
            } else {
                holder.radioBtn.setChecked(false);
            }


       /*     holder.radioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //updateList(position);
                    holder.radioBtn.setChecked(true);
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //     updateList(position);
                    holder.radioBtn.setChecked(true);

                }
            });*/
            return convertView;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public Filter getFilter() {
            return new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    arrayList = (ArrayList<CommanDTO>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<CommanDTO> FilteredArrList = new ArrayList<>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<>(arrayList); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getName();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(mOriginalValues.get(i));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
        }
    }


    public interface LimitExceedListener {
        void onLimitListener(CommanDTO data);
    }


}
