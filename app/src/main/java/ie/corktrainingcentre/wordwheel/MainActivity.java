package ie.corktrainingcentre.wordwheel;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {


    private final String TAG = "MainActivity";
    private final boolean   LOGS_ON = true;
    TextView textView;
    TextView textView2;
    EditText userInputEditText;
    Button checkWordButton;
    Spinner spinner;
    WordWheel wordWheel;
    final String[] lengthsOfWord = {"9", "10","11", "12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intializeObjectsFromXML_Resourses();





        // Add a on selected listener to the spinner
        /*spinner.setOnClickListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {

                //wordWheel.setWordlenght(Integer.valueOf((String)spinner.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // TODO Auto-generated method stub

            }



        });*/

        wordWheel.setWordlenght(Integer.valueOf((String) spinner.getSelectedItem()));
        textView.setText(wordWheel.getWord());
        //textView2.setText(String.valueOf(wordWheel.getCentreChar()));
        textView2.setText(wordWheel.scrambledWord(wordWheel.getWord()));


    }
    private void intializeObjectsFromXML_Resourses()
    {
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        userInputEditText = (EditText) findViewById(R.id.editText);
        checkWordButton = (Button) findViewById(R.id.checkWordButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,lengthsOfWord);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Must be done after spinner has been intialized
        wordWheel = new WordWheel(Integer.valueOf((String) spinner.getSelectedItem()));

    }


}
