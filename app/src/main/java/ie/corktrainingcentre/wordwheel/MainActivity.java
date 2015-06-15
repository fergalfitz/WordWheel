package ie.corktrainingcentre.wordwheel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {


    private final String TAG = "MainActivity";
    private final boolean   LOGS_ON = true;
//    TextView textView;
//    TextView textView2;
    EditText userInputEditText;
    Button checkWordButton;
    Button displayWheelButton;
    Button solutionsButton;
    Spinner spinner;
    WordWheel wordWheel;
    TextView resultsTextView;
    LinearLayout wordWheelLinearLayout;
    final String[] lengthsOfWords = { "7","8","9", "10","11", "12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intializeObjectsFromXML_Resourses();

        // add action listener to the Display Wheel button
        displayWheelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshWordWheel();
            }
        });

        // add onClickListener to the Checkword Button
        checkWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString().toLowerCase().trim();
//
//                guess word must be equal or greater than four characters
                if(userInput.length() < 4){

                    Toast.makeText(getApplicationContext(), "Guess with four characters or more ;)",
                            Toast.LENGTH_SHORT).show();
                    userInputEditText.setText("");
                }
                else if (!userInput.contains(String.valueOf(wordWheel.getCentreChar()))){
                    Toast.makeText(getApplicationContext(), "Guess does not contain \"" +
                                    wordWheel.getCentreChar() + "\"",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    Result result = wordWheel.checkWord(userInput);
                    resultsTextView.setText("\n" + result.getMessage());
                }
            }
        });

        // add onClickListener to the Solutions button
        solutionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String solutions = wordWheel.solutions(wordWheel.getWord());
                resultsTextView.setText(solutions);

            }
        });


        refreshWordWheel();
    }

    private void refreshWordWheel()
    {
        wordWheel.setWordlenght(Integer.valueOf((String) spinner.getSelectedItem()));
//        textView.setText(wordWheel.getWord());
//        textView2.setText(wordWheel.scrambledWord(wordWheel.getWord()));
//        textView2.append("--" + wordWheel.getCentreChar());
        resultsTextView.setText("");
        wordWheelLinearLayout.removeAllViews();
        wordWheelLinearLayout.addView(new wordWheelView(this));
    }
    private void intializeObjectsFromXML_Resourses()
    {
//        textView = (TextView) findViewById(R.id.textView);
//        textView2 = (TextView) findViewById(R.id.textView2);
        userInputEditText = (EditText) findViewById(R.id.editText);
        checkWordButton = (Button) findViewById(R.id.checkWordButton);
        displayWheelButton = (Button) findViewById(R.id.display);
        solutionsButton = (Button) findViewById(R.id.solutionsButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        wordWheelLinearLayout = (LinearLayout) findViewById(R.id.wordWheelLinearLayout);

//        set the height of wordWheelLinearLayout to half of the screen
        ViewGroup.LayoutParams params = wordWheelLinearLayout.getLayoutParams();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        params.height = height /3;
        wordWheelLinearLayout.addView(new wordWheelView(this));


       /* // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,lengthsOfWord);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Must be done after spinner has been intialized*/


        ArrayAdapter adapter = new CustomArrayAdapter<CharSequence>(this, lengthsOfWords);
        spinner.setAdapter(adapter);

        // TODO fix the spinner
        wordWheel = new WordWheel(Integer.valueOf((String) spinner.getSelectedItem()));


        // Add a on selected listener to the spinner
        /*spinner.setOnClickListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {

                refreshWordWheel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });*/

    }

    public class wordWheelView extends View {

        public wordWheelView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);

            int x = getWidth();
            int y = getHeight();

            Paint paint = new Paint();


            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPaint(paint);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(2);
//            paint.
            canvas.drawCircle(x / 2, y / 2, y / 2, paint);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(30);
            canvas.drawCircle(x / 2, y / 2, y / 8, paint);


//            draw the center character
            int offsetForChar = 10;

            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            paint.setStrokeWidth(2);
            canvas.drawText(String.valueOf(wordWheel.getCentreChar()), (x / 2) -offsetForChar ,
                    (y / 2) + offsetForChar, paint);

//              remove the center character from the letters to be displayed on the circle
            StringBuffer scrambledWord = new StringBuffer(wordWheel.scrambledWord(wordWheel.getWord()));
            scrambledWord.deleteCharAt(scrambledWord.indexOf(String.valueOf(wordWheel.getCentreChar())));

//            final int totalAngle = 360;
//            int angleIncrements = 360 / scrambledWord.length();
            double slice = 2 * Math.PI / scrambledWord.length();
            int radius = y/ 3;
            int xPosition = 0;
            int yPosition = 0;

            for(int i = 0; i < scrambledWord.length() ; i++)
            {
                double angle = slice * i;
                xPosition = (int)(radius* Math.cos(angle) + (x / 2) - offsetForChar);
                yPosition = (int)(radius* Math.sin(angle)+ (y / 2) +offsetForChar);
                char c = scrambledWord.charAt(i);
                String letter = String.valueOf(c);

                canvas.drawText(letter, xPosition,yPosition, paint);

            }





        }
    }

    static class CustomArrayAdapter<T> extends ArrayAdapter<T>
    {
        public CustomArrayAdapter(Context ctx, T [] objects)
        {
            super(ctx, android.R.layout.simple_spinner_item, objects);
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);

            //we know that simple_spinner_item has android.R.id.text1 TextView:

        /* if(isDroidX) {*/
            TextView text = (TextView)view.findViewById(android.R.id.text1);
            text.setTextColor(Color.BLACK);//choose your color :)
        /*}*/

            return view;

        }
    }
}
