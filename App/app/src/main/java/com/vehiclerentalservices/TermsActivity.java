package com.vehiclerentalservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class TermsActivity extends AppCompatActivity {

    TextView topic, term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        topic = findViewById(R.id.txtTopic);
        topic.setText("Terms and Conditions");

        String text = "<b>1. Introduction <br/></b>" +
                "        <i>  We built this app that enable people to book vehicles, act like owners and provides financial benefits." +
                "         We do not charge you to use this app but owner has to pay us certain percent from their benefits as we provide some services. We use your personal data and we don't sell it to anyone.  " +
                "        </i>" +
                "        <b><br/><br/>2. Necessities to use this app</b>" +
                "        <i><br/>  a) You must be above 15 years old.<br/>" +
                "         b) Provide accurate information about yourself.<br/>" +
                "         c) Provide for your account the same name that you use everyday life.<br/>" +
                "              d) Create only one account and use it for personal purpose.<br/><br/></i>" +
                "        <b>3. The permissions you give to us<br/></b>" +
                "       <i>   a) Permission to use your name,picture,and information about your action.<br/>" +
                "         b) Permission to update software to use or download.<br/>" +
                "             c) Permission of internet and file access.<br/><br/></i>" +
                "        <b>4. Responsibilities and duties<br/></b>" +
                "      <i>    If there is any illegal act or accidental cases occurs with owners vehicles, we the developer of this app are not responsible for that incident and we do not provide any compensation but we will cooperate for further help with police officers according to our company's rules and regulations." +
                "      </i>";

        term= findViewById(R.id.txtTerms);
        term.setText(Html.fromHtml(text));
    }
}