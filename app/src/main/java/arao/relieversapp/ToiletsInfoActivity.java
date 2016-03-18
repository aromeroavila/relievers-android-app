package arao.relieversapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import arao.relieversapp.model.Room;
import arao.relieversapp.net.Book;
import arao.relieversapp.net.Maintenance;
import arao.relieversapp.net.RoomsStatus;
import arao.relieversapp.net.ServiceGenerator;
import retrofit2.Call;

public class ToiletsInfoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RoomListAdapter mRoomListAdapter;
    private TextView mNextInTheQueue;
    private TextView mYouAreInTheQueue;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_toilets_info);

        mRoomListAdapter = new RoomListAdapter();
        mNextInTheQueue = (TextView) findViewById(R.id.next_in_the_queue);
        mYouAreInTheQueue = (TextView) findViewById(R.id.you_are);
        ListView roomList = (ListView) findViewById(R.id.rooms_list);
        roomList.setAdapter(mRoomListAdapter);
        roomList.setOnItemClickListener(this);
        findViewById(R.id.book_button).setOnClickListener(this);

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        new RoomsRequestExecutor().execute();
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000); //it executes this every 1000ms
    }

    @Override
    public void onClick(View v) {
        new BookRequestExecutor().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Room item = (Room) mRoomListAdapter.getItem(position);
        if (item.isAvailable() && !item.isInQuarantine()) {
            new AlertDialog.Builder(ToiletsInfoActivity.this)
                    .setTitle("pOops!!")
                    .setMessage("Would you like to repoort a pooblem?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new MaintenanceRequestExecutor().execute(item.getId());
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public class BookRequestExecutor extends AsyncTask<Void, Void, int[]> {

        @Override
        protected int[] doInBackground(Void... params) {
            Book service = ServiceGenerator.createService(Book.class);
            Call<int[]> book = service.book();
            int[] result = null;

            try {
                result = book.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(int[] rooms) {
            if (rooms != null) {
                mNextInTheQueue.setText("Calling now:   " + rooms[0]);
                mYouAreInTheQueue.setText("You are in position:   " + rooms[rooms.length - 1]);
            }
        }
    }

    public class RoomsRequestExecutor extends AsyncTask<Void, Void, List<Room>> {

        @Override
        protected List<Room> doInBackground(Void... params) {
            RoomsStatus service = ServiceGenerator.createService(RoomsStatus.class);
            Call<List<Room>> rooms = service.rooms();
            List<Room> result = null;

            try {
                result = rooms.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Room> rooms) {
            if (rooms != null) {
                mRoomListAdapter.setData(rooms);
            }
        }
    }

    public class MaintenanceRequestExecutor extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Maintenance service = ServiceGenerator.createService(Maintenance.class);
            Call<Void> report = service.report(params[0]);
            try {
                report.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {

        }
    }

}
