package com.habitrpg.android.habitica.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.habitrpg.android.habitica.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by franzejr on 04/11/15.
 */
public class AboutFragment extends Fragment {

    private String androidSourceCodeLink = "https://github.com/HabitRPG/habitrpg-android/";
    private String twitterLink = "https://twitter.com/habitica";

    @Bind(R.id.versionInfo)
    public TextView versionInfo;

    @OnClick(R.id.sourceCodeLink)
    public void openSourceCodePageByLabel() {
        openBrowserLink(androidSourceCodeLink);
    }

    @OnClick(R.id.twitter)
    public void openTwitterPage() {
        openBrowserLink(twitterLink);
    }

    @OnClick(R.id.sourceCodeButton)
    public void openSourceCodePageByButton() {
        openBrowserLink(androidSourceCodeLink);
    }

    @OnClick(R.id.reportBug)
    public void sendBugReport() {
        sendEmail("[Android] Bugreport");
    }

    @OnClick(R.id.sendFeedback)
    public void sendFeedback() {
        sendEmail("[Android] Feedback");
    }


    @OnClick(R.id.googlePlayStoreButton)
    public void openGooglePlay() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.habitrpg.android.habitica"));
        startActivity(intent);
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(R.layout.fragment_about, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        Activity activity = getActivity();
        try {
            String versionName = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            int versionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;

            this.versionInfo.setText(getString(R.string.version_info, versionName, versionCode));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void openBrowserLink(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void sendEmail(String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","mobile@habitica.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
