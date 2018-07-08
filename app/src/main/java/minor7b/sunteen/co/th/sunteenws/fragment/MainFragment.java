package minor7b.sunteen.co.th.sunteenws.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import minor7b.sunteen.co.th.sunteenws.R;
import minor7b.sunteen.co.th.sunteenws.ServiceActivity;
import minor7b.sunteen.co.th.sunteenws.utility.GetAllData;
import minor7b.sunteen.co.th.sunteenws.utility.MyAlertDialog;
import minor7b.sunteen.co.th.sunteenws.utility.MyConstant;

public class MainFragment extends Fragment{

    

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

// Register Controller
        registerController();


//        Login Controller
        loginController();


    } // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText usereditText = getView().findViewById(R.id.edtUser);
                EditText passwordeditText = getView().findViewById(R.id.edtPassword);

                String userString = usereditText.getText().toString().trim();
                String passwordString = passwordeditText.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()) {

                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    myAlertDialog.normalDialog("Blank Field Detected","Please Fill All Fields");

                } else {

                    MyConstant myConstant = new MyConstant();
                    boolean aBoolean = true;
                    String truePassword = null;
                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());

                    try {

                        GetAllData getAllData = new GetAllData(getActivity());
                        getAllData.execute(myConstant.getUrlGetAllUser());

                        String jsonString = getAllData.get();
                        Log.d("8julyV1", "JSON ==> " + jsonString);

                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i=0; i<jsonArray.length(); i+=1) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (userString.equals(jsonObject.getString("User"))) {

                                truePassword = jsonObject.getString("Password");
                                aBoolean = false;


                            }


                        }

                        if (aBoolean) {
                            myAlertDialog.normalDialog("No User Found", "Cannot find " + userString + " in the database");
                        } else if (passwordString.equals(truePassword)) {
                            // Password True
                            Toast.makeText(getActivity(),"Welcome to My App", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), ServiceActivity.class));
                            getActivity().finish();

                        } else {
                            myAlertDialog.normalDialog("Password Failed","Please Fill The Password Again");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } // if

            }
        });
    }

    private Button getViewById() {
        return getView().findViewById(R.id.btnLogin);
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
} // Main Class
