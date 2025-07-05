package com.aube.mysize.presentation.ui.component.ad

import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.aube.mysize.R
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAdGridItemView(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        factory = { context ->
            val inflater = LayoutInflater.from(context)
            val adView = inflater.inflate(R.layout.native_ad_grid_item, null) as NativeAdView

            val adLoader = AdLoader.Builder(context, adUnitId)
                .withNativeAdOptions(
                    NativeAdOptions.Builder()
                        .setRequestMultipleImages(false)
                        .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                        .build()
                )
                .forNativeAd { nativeAd ->
                    val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
                    adView.mediaView = mediaView

                    val headline = adView.findViewById<TextView>(R.id.ad_headline)
                    headline.text = nativeAd.headline
                    adView.headlineView = headline

                    adView.setNativeAd(nativeAd)
                }
                .build()

            adLoader.loadAd(AdRequest.Builder().build())
            adView
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NativeAdGridItemViewPreview() {
    val testAdUnitId = "ca-app-pub-3940256099942544/2247696110"
    NativeAdGridItemView(adUnitId = testAdUnitId)
}
