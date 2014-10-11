package org.xhan.subtitleplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.xhan.subtitleplayer.R;
import org.xhan.subtitleplayer.service.ControllerServiceConnection;
import org.xhan.subtitleplayer.service.IServiceConnectionHandler;
import org.xhan.subtitleplayer.service.SubPlayerService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ControlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance() {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public ControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_control, container, false);
        Button startServiceButton = (Button)rootView.findViewById(R.id.ctrl_start_service_btn);
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), SubPlayerService.class));
            }
        });
        Button stopServiceButton = (Button)rootView.findViewById(R.id.ctrl_stop_service_btn);
        stopServiceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), SubPlayerService.class));
            }
        });

        Button bindButton = (Button)rootView.findViewById(R.id.ctrl_bind_btn);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ControllerServiceConnection serviceConnection = new ControllerServiceConnection(new IServiceConnectionHandler<ControllerServiceConnection>() {
                    @Override
                    public void onConnected(ControllerServiceConnection connection) {
                        Toast.makeText(getActivity(), "service connected", Toast.LENGTH_LONG).show();
                        connection.openFile("~/.bashrc");
                    }
                });
                getActivity().bindService(SubPlayerService.getControllerBindIntent(getActivity()), serviceConnection, Context.BIND_AUTO_CREATE);

            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
