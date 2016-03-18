package arao.relieversapp;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import arao.relieversapp.app.RelieversApplication;
import arao.relieversapp.model.Room;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RoomListAdapter extends BaseAdapter {

    private List<Room> mPreviousRoomList;
    private List<Room> mRoomList;
    private Resources mResources;

    RoomListAdapter() {
        mResources = RelieversApplication.getContext().getResources();
    }

    public void setData(List<Room> roomList) {
        mPreviousRoomList = mRoomList;
        mRoomList = roomList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mRoomList != null) {
            return mRoomList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mRoomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_info_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.item = (LinearLayout) convertView;
            viewHolder.title = (TextView) convertView.findViewById(R.id.room_title);
            viewHolder.status = (TextView) convertView.findViewById(R.id.room_status);
            viewHolder.time = (TextView) convertView.findViewById(R.id.room_timer);
            viewHolder.chronometer = (Chronometer) convertView.findViewById(R.id.room_clock);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Room room = mRoomList.get(position);

        if (mPreviousRoomList == null) {
            fillView(viewHolder, room);
        } else if (mPreviousRoomList.get(position).isAvailable() != room.isAvailable()) {
            fillView(viewHolder, room);
        }

        return convertView;
    }

    private void fillView(ViewHolder viewHolder, Room room) {
        viewHolder.title.setText(room.getName());
        viewHolder.status.setText(room.isAvailable() ? "AVAILABLE" : "BUSY");
        viewHolder.item.setBackground(room.isAvailable() ?
                mResources.getDrawable(R.drawable.green_round_corners) : mResources.getDrawable(R.drawable.red_round_corners));

        viewHolder.time.setText(room.isAvailable() ?
                "Run!! It's been free for " : "Colleague slacking for ");
        viewHolder.chronometer.stop();
        viewHolder.chronometer.setBase(SystemClock.elapsedRealtime());
        viewHolder.chronometer.start();
    }

    static class ViewHolder {
        LinearLayout item;
        TextView title;
        TextView status;
        TextView time;
        Chronometer chronometer;
    }

}