package fr.insa_rennes.greensa.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.insa_rennes.greensa.MainActivity;
import fr.insa_rennes.greensa.R;
import fr.insa_rennes.greensa.database.CoursesLoader;
import fr.insa_rennes.greensa.database.model.Course;

/**
 * Cette classe affiche un spinner permettant de choisir le parcours sur lequel l'utilisateur veut jouer.<br/>
 * L'id du parcours choisi est ensuite transmis a l'activite MapsActivity
 */
public class ChoiceCourseActivity extends Activity {

    /**
     * Liste deroulante des parcours
     */
    private Spinner coursesSpinner = null;

    /**
     * Liste contenant les parcours
     */
    private List<Course> courses = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_course);

        ImageView home = (ImageView)findViewById(R.id.homeButton);
        Button start = (Button)findViewById(R.id.startButton);
        coursesSpinner = (Spinner)findViewById(R.id.coursesSpinner);

        // On charge les noms des parcours dans le spinner
        courses = CoursesLoader.getCourses();

        List<String> list = new ArrayList<String>();
        for(Course course : courses){
            list.add(course.getName());
        }

        ArrayAdapter<String> adapterCourses = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        coursesSpinner.setAdapter(adapterCourses);

        home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent activity = new Intent(ChoiceCourseActivity.this, MainActivity.class);
                startActivity(activity);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent activity = new Intent(ChoiceCourseActivity.this, MapsActivity.class);

                int id = 0;
                // On recupere le parcours selectionne
                String elem = (String) coursesSpinner.getSelectedItem();

                for(Course course : courses){
                    if(course.getName().equals(elem)) {
                        id = course.getId();
                        break;
                    }
                }
                // On transmet l'id du parcours selectionne
                activity.putExtra("id_parcours", id);
                startActivity(activity);
            }

        });
    }
}
