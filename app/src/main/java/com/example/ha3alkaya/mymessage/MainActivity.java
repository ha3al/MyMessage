package com.example.ha3alkaya.mymessage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyCustomAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }

    private class MyCustomAdapter extends ArrayAdapter<Person> {

        private ArrayList<Person> personList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Person> personList) {
            super(context, textViewResourceId, personList);
            this.personList = new ArrayList<Person>();
            this.personList.addAll(personList);
        }

        private class ViewHolder {
            TextView tel;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.person_info, null);

                holder = new ViewHolder();
                holder.tel = (TextView) convertView.findViewById(R.id.tel);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v ;
                        Person _person = (Person) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        _person.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Person person = personList.get(position);
            holder.tel.setText(" (" +  person.getTel() + ")");
            holder.name.setText(person.getName());
            holder.name.setChecked(person.isSelected());
            holder.name.setTag(person);

            return convertView;

        }

    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Person> personList = new ArrayList<Person>();
        Person person = new Person("Hazal KAYA","123 11 22",false);
        personList.add(person);
        person = new Person("Özcan Yarımdünya","124 11 22",false);
        personList.add(person);
        person = new Person("Öykü Çam","125 11 22",false);
        personList.add(person);
        person = new Person("Sinem Alsan","126 11 22",false);
        personList.add(person);


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.person_info, personList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Person person = (Person) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + person.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String _text = "";
                Boolean flag = false;

                //StringBuffer responseText = new StringBuffer();
                EditText _input = (EditText) findViewById(R.id.editinput);
                //responseText.append("The following were selected...\n");

                ArrayList<Person> personList = dataAdapter.personList;

                for (int i = 0; i < personList.size(); i++) {

                    Person person = personList.get(i);

                    if (person.isSelected()) {

                        _text = _input.getText().toString();
                        if (_text.contains("[ISIM]")) {

                            _text = (_text.replace("[ISIM]", person.getName()));
                        }
                        // responseText.append("\n" + person.getName());

                        Intent mesaj = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + person.getTel()));
                        mesaj.putExtra("sms_body", _text);
                        startActivity(mesaj);
                        flag = true;
                    }

                }

                if (flag == false)
                    Toast.makeText(MainActivity.this, "Lütfen Kişi Seçimi Yapınız!", Toast.LENGTH_SHORT).show();

                // Toast.makeText(getApplicationContext(),
                // responseText, Toast.LENGTH_LONG).show();
                //test

            }
        });

    }
}
