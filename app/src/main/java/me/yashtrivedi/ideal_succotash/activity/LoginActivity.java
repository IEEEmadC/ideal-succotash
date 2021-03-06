package me.yashtrivedi.ideal_succotash.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.ImageHelper;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.model.User;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnClickListener {

    private static final int RC_SIGN_IN = 100;
    TextView mStatusTextView;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private ImageView imageView;
    private ProgressBar imageProgress;
    private Firebase firebase;
    private GoogleSignInAccount acct;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;
    private String mEncodedEmail;
    private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        firebase = new Firebase(Constants.FIREBASE_URL);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefEditor = mSharedPref.edit();
        imageView = (ImageView) findViewById(R.id.google_icon);
        imageProgress = (ProgressBar) findViewById(R.id.image_progress);
        mStatusTextView = (TextView) findViewById(R.id.status);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.getBoolean("LogOutPlease",false)){
            //signOut();
        }
        signInButton.setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("D", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess() && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("logout", false)) {
            acct = result.getSignInAccount();
            if (acct.getEmail().split("@")[1].equals("nirmauni.ac.in")) {
                ((TextView) findViewById(R.id.title_text)).setText(acct.getDisplayName());
                mStatusTextView.setText(getResources().getString(R.string.signed_in_fmt, acct.getEmail()));
                try {
                    new AsyncTask<String, Void, Bitmap>() {

                        @Override
                        protected void onPreExecute() {
                            imageProgress.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected Bitmap doInBackground(String... params) {

                            try {
                                URL url = new URL(params[0]);
                                InputStream in = url.openStream();
                                return BitmapFactory.decodeStream(in);
                            } catch (Exception e) {
                                //* TODO log error *//
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Bitmap bitmap) {
                            imageProgress.setVisibility(View.INVISIBLE);
                            try {
                                imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap,
                                        imageView.getWidth()));
                            } catch (NullPointerException e) {
                            }
                        }
                    }.execute(acct.getPhotoUrl().toString());
                }catch(NullPointerException ne){}
                updateUI(true);
                getGoogleOAuthTokenAndLogin();
            } else {
                revokeAccess();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.invalid_ac, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signIn();
                    }
                });
                snackbar.show();
            }
        } else {
            updateUI(false);
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("logout",false).apply();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            ((TextView) findViewById(R.id.title_text)).setText(R.string.title_text);
            imageView.setImageResource(R.drawable.googleg_color);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.conn_prob, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Okay", new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void setAuthenticatedUserGoogle(final AuthData authData) {
        String unprocessedEmail;
        if (mGoogleApiClient.isConnected()) {
            unprocessedEmail = acct.getEmail().toLowerCase();
            mSharedPrefEditor.putString(Constants.KEY_GOOGLE_EMAIL, unprocessedEmail).apply();
        } else {
            unprocessedEmail = mSharedPref.getString(Constants.KEY_GOOGLE_EMAIL, null);
        }

        mEncodedEmail = BaseApplication.utils.encodeEmail(unprocessedEmail);
        final String userName = acct.getDisplayName();
        //final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);

        HashMap<String, Object> userAndUidMap = new HashMap<>();

        HashMap<String, Object> newUserMap = new HashMap<>();
        newUserMap.put("name",userName);
        newUserMap.put("email",mEncodedEmail);
        newUserMap.put("timeStampJoined",ServerValue.TIMESTAMP);
        userAndUidMap.put("/" + Constants.FIREBASE_LOCATION_UID_MAP + "/"
                + authData.getUid(), mEncodedEmail);
        Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(mEncodedEmail));
        firebase1.updateChildren(newUserMap, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError!=null)
                    Log.d("error",firebaseError.toString());
            }
        });
        firebase.updateChildren(userAndUidMap, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    /* Try just making a uid mapping */
                    firebase.child(Constants.FIREBASE_LOCATION_UID_MAP)
                            .child(authData.getUid()).setValue(mEncodedEmail);
                }
            }
        });
    }

    private void loginWithGoogle(String token) {
        //Log.d("there","here");
        firebase.authWithOAuthToken(Constants.GOOGLE_PROVIDER, token, new MyAuthHandler(Constants.GOOGLE_PROVIDER));
    }

    private void getGoogleOAuthTokenAndLogin() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    String scope = String.format(getString(R.string.oauth2_format), new Scope(Scopes.PROFILE)).concat(" email");
                    token = GoogleAuthUtil.getToken(LoginActivity.this, acct.getEmail(), scope);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return token;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null)
                    loginWithGoogle(s);
                //Log.d("hi","here");
            }
        };
        task.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebase.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {


                /**
                 * If there is a valid session to be restored, start MainActivity.
                 * No need to pass data via SharedPreferences because app
                 * already holds userName/provider data from the latest session
                 */

                Log.d("email",BaseApplication.utils.getMyEmail());
                Log.d("name",BaseApplication.utils.getMyName());
                if (authData != null && BaseApplication.utils.getMyEmail()!=null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
                    if (opr.isDone()) {
                        Log.d("D", "Got cached sign-in");
                        GoogleSignInResult result = opr.get();
                        handleSignInResult(result);
                    } else {
                        showProgressDialog();
                        opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                            @Override
                            public void onResult(GoogleSignInResult googleSignInResult) {
                                hideProgressDialog();
                                handleSignInResult(googleSignInResult);
                            }
                        });
                    }
                }
            }
        };
        /* Add auth listener to Firebase ref */
        firebase.addAuthStateListener(mAuthStateListener);
    }

    private class MyAuthHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public MyAuthHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            if (authData != null) {
                //Log.d("hi","there");
                if (authData.getProvider().equals(Constants.GOOGLE_PROVIDER)) {
                    setAuthenticatedUserGoogle(authData);
                }

                mSharedPrefEditor.putString(Constants.KEY_PROVIDER, authData.getProvider()).apply();
                mSharedPrefEditor.putString(Constants.KEY_ENCODED_EMAIL, mEncodedEmail).apply();
                mSharedPrefEditor.putString(Constants.KEY_NAME, acct.getDisplayName()).apply();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {

        }
    }
}