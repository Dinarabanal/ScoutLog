package edu.cnm.deepdive.scoutlog.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.scoutlog.R;


public class AddScout extends Fragment {



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_add_scout, container, false);
    final EditText setFirstName = view.findViewById(R.id.first_name);
    EditText setLastName = view.findViewById(R.id.last_name);
    Button saveButton =view.findViewById(R.id.save_button);
    saveButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
       String firstName = setFirstName.getText().toString();

      }
    });

    return view;
  }

}
