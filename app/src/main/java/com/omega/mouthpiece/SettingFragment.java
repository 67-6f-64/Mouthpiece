package com.omega.mouthpiece;

//import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
//import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private Switch simpleSwitch1;
    private ConstraintLayout mLinearLayout;
    private Button deleteBtn;
    private Button signIn;
    private Button signOut;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


   // private Button feedback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        View rootV2 = inflater.inflate(R.layout.fragment_landing, container, false);
        simpleSwitch1 = root.findViewById(R.id.themeSwitch);
        simpleSwitch1.setChecked(GlobalVariableMode.mode);
        mLinearLayout = root.findViewById(R.id.viewConstr);

        deleteBtn = root.findViewById(R.id.deleteProfileBtn);
        signIn = root.findViewById(R.id.settingsLogIn);
        signOut = root.findViewById(R.id.logOutBtn);
        //feedback = root.findViewById(R.id.feedback);
        setTheme();

        simpleSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GlobalVariableMode.mode = true;
                    setTheme();
                }
                else{
                    GlobalVariableMode.mode = false;
                    setTheme();
                }
            }
        });
        sharedPreferences=getContext().getSharedPreferences("LoginPrefs",getContext().MODE_PRIVATE);
        editor=sharedPreferences.edit();
        signOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.putString("Email","");
                editor.putString("Password","");
                editor.putBoolean("Remember",false);
                editor.putString("API", "");
                editor.commit();

                Intent intent = new Intent(getContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void setTheme() {
        if(GlobalVariableMode.mode == true){

            mLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
             simpleSwitch1.setTextColor(Color.parseColor("#FFFFFF"));
            //button colours
            deleteBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            deleteBtn.setTextColor(Color.parseColor("#000000"));
            //button colours
            signIn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            signIn.setTextColor(Color.parseColor("#000000"));

        }
        else{

            mLinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            simpleSwitch1.setTextColor(Color.parseColor("#000000"));
            //button colours
            deleteBtn.setBackgroundColor(Color.parseColor("#000000"));
            deleteBtn.setTextColor(Color.parseColor("#FFFFFF"));
            //button colours
            signIn.setBackgroundColor(Color.parseColor("#000000"));
            signIn.setTextColor(Color.parseColor("#FFFFFF"));

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //setMode();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(sharedPreferences.getString("Email","")=="")
        {
            getActivity().findViewById(R.id.deleteProfileBtn).setVisibility(View.GONE);
            getActivity().findViewById(R.id.settingsLogIn).setVisibility(View.GONE);
        }
        else
        {
            getActivity().findViewById(R.id.deleteProfileBtn).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.settingsLogIn).setVisibility(View.VISIBLE);
        }
    }

   }

