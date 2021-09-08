package com.android.exemple;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;


import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.android.exemple.R;

import static com.ironsource.mediationsdk.IronSource.isInterstitialReady;

public class AdsHelper {

    private final Activity activity;
    private IronSourceBannerLayout banner;

    public AdsHelper(Activity p_activity) {
        activity = p_activity;
    }


    public void createBanner() {
        banner = IronSource.createBanner(activity, ISBannerSize.BANNER);
        final FrameLayout frameLayout = activity.findViewById(R.id.bannerContainer);
        if(frameLayout != null){
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            frameLayout.addView(banner, 0, layoutParams);

            banner.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {
                    
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                    
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            frameLayout.removeAllViews();
                        }
                    });
                }

                @Override
                public void onBannerAdClicked() {
                    
                }

                @Override
                public void onBannerAdScreenPresented() {
                    
                }

                @Override
                public void onBannerAdScreenDismissed() {
                    
                }

                @Override
                public void onBannerAdLeftApplication() {
                    
                }
            });
            IronSource.loadBanner(banner);
        }
    }

    public void destroyBanner() {
        IronSource.destroyBanner(banner);
    }

    public void loadInter(final boolean reload) {
        IronSource.setInterstitialListener(new InterstitialListener() {
            
            @Override
            public void onInterstitialAdReady() {
            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
            }

            @Override
            public void onInterstitialAdOpened() {
            }

            @Override
            public void onInterstitialAdClosed() {

                if (reload) {

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            loadInter();
                        }
                    }, 5000);
                }
            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {
            }

            @Override
            public void onInterstitialAdClicked() {
            }

            @Override
            public void onInterstitialAdShowSucceeded() {
            }
        });

        IronSource.loadInterstitial();
    }

    public void loadInter() {
        loadInter(false);
    }

    public void showInter() {

        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (isInterstitialReady()) {
                    IronSource.showInterstitial(activity.getString(R.string.ironsource_interstitial_id));
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });
    }

}

//////////


////////// Layout
// <FrameLayout
//     android:id="@+id/bannerContainer"
//     android:layout_width="match_parent"
//     android:layout_height="wrap_content"
//     android:gravity="center"
//     android:orientation="vertical"
//     android:layout_alignParentBottom="true">
// </FrameLayout>

///////// REquered for all Activities using banner:
//@Override
// protected void onPause() {
//     IronSource.onPause(this);
//     ads.destroyBanner();
//     super.onPause();
// }

// @Override
// protected void onResume() {
//     super.onResume();
//     IronSource.onResume(this);
//     ads.createBanner();
// }