package edu.cnm.deepdive.scoutlog.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;


/**
 * The type Add scout.
 * sub class
 * this is where the scout is added to the fragment
 */
public class AddScout extends Fragment {

    /**
     * The String rank.
     */
    String stringRank = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_scout, container, false);
        final EditText setFirstName = view.findViewById(R.id.first_name);
        final EditText setLastName = view.findViewById(R.id.last_name);
        final RadioGroup rank = view.findViewById(R.id.rank);
        final CircleImageView saveButton = view.findViewById(R.id.save_button);
        rank.setOnCheckedChangeListener(new OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rank.findViewById(checkedId);
                int index = rank.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                        stringRank = "Cub";
                        break;
                    case 1:
                        stringRank = "Boy";
                        break;
                    case 2:
                        stringRank = "Eagle";
                        break;
                    default:
                        stringRank = "UNDEFINED";
                }
            }
        });

        saveButton.setOnClickListener(new OnClickListener() {



            @Override
            public void onClick(View v) {

                String firstName = setFirstName.getText().toString();
                String lastName = setLastName.getText().toString();
                if (firstName.matches("") || lastName.matches("")) {
                    Toast.makeText(getContext(), "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Scout scout = new Scout();
                    scout.setFullName(firstName, lastName);
                    scout.setRank(stringRank);
                    new InsertTask().execute(scout);
                }
            }
        });

        return view;

    }

    private class InsertTask extends AsyncTask<Scout, Void, Long> {

        @Override
        protected Long doInBackground(Scout... scouts) {
            return ScoutLogDatabase.getInstance(getContext()).getScoutDao().insert(scouts[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            switchFragment(new ScoutFragment(), true,"");
        }
    }

    public void switchFragment(Fragment fragment, boolean useStack, String variant) {
        FragmentManager manager = getFragmentManager();
        String tag = fragment.getClass().getSimpleName() + ((variant != null) ? variant : "");
        if (manager.findFragmentByTag(tag) != null) {
            manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        if (useStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

}
