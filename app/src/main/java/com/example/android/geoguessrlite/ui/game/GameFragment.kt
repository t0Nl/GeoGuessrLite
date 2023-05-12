package com.example.android.geoguessrlite.ui.game

import android.animation.ValueAnimator
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.android.geoguessrlite.R
import com.example.android.geoguessrlite.databinding.FragmentGameBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

private const val DEFAULT_RESULT_BOUNDS_PADDING_VALUE = 280
private val DEFAULT_LAT_LNG = LatLng(0.0, 0.0)

private const val FINAL_RESULT_ARRAY_SIZE = 3
private const val RESULT_ZOOM_LEVEL = 2f
private const val STREET_VIEW_RADIUS = 10000

private const val HIDE_RESULT_ALPHA = 0f
private const val SHOW_RESULT_ALPHA = .7f
private const val VISIBILITY_TRANSITION_DURATION = 2000L

class GameFragment : Fragment(), OnMapReadyCallback, OnStreetViewPanoramaReadyCallback {
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    private lateinit var binding: FragmentGameBinding
    private lateinit var map: GoogleMap
    private lateinit var streetView: StreetViewPanorama

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.guessButton.isEnabled = false
        binding.pinButton.isEnabled = false
        binding.mapButton.isEnabled = true

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val streetViewFragment =
            childFragmentManager.findFragmentById(R.id.streetView) as SupportStreetViewPanoramaFragment
        streetViewFragment.getStreetViewPanoramaAsync(this)

        binding.guessButton.setOnClickListener {
            if (viewModel.guessCompleted.value == true) {
                resetMapView()

                binding.guessButton.text = getString(R.string.guess_label)
                binding.guessButton.isEnabled = false

                transitionToStreetView()
                hideGuessResult()
//                viewModel.loadNextLocation()
            } else {
                binding.guessButton.text = getString(R.string.continue_label)

                transitionToMap()
                rateGuessAccuracy()
                disableMapClicks()
                showGuessResult()
                viewModel.loadNextLocation()
            }
        }

        binding.mapButton.setOnClickListener {
            transitionToMap()
        }

        binding.pinButton.setOnClickListener {
            transitionToStreetView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.streetViewLocation.observe(viewLifecycleOwner) { guessLocation ->
            guessLocation?.let {
                streetView.setPosition(it, STREET_VIEW_RADIUS)
            }
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) {
            if (it) {
                findNavController(this).navigate(
                    GameFragmentDirections.actionGameFragmentToResultFragment(
                        viewModel.gameScore.value?.toLong() ?: 0L,
                        "World",
                        60,
                    )
                )
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        binding.map.translationX = binding.map.measuredWidth.toFloat()

        map.setOnMapClickListener { latLng ->
            binding.guessButton.isEnabled = true
            viewModel.currentMarker.value?.remove()
            viewModel.updateCurrentMarker(
                map.addMarker(
                    MarkerOptions().position(latLng)
                )
            )
        }
    }

    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
        streetView = streetViewPanorama
        streetView.isStreetNamesEnabled = false
        streetView.isUserNavigationEnabled = false

        streetView.setOnStreetViewPanoramaChangeListener {
            viewModel.startTimer()
        }
    }

    private fun showGuessResult() {
        showGuessResultAnimator(binding.guessPointsDisplay)
        showGuessResultAnimator(binding.guessPointsLabel)
        showGuessResultAnimator(binding.totalScoreDisplay)
        showGuessResultAnimator(binding.totalScoreLabel)
        showGuessResultAnimator(binding.resultRoundedCornerBackground)
    }

    private fun showGuessResultAnimator(view: View) {
        val va = ValueAnimator.ofFloat(HIDE_RESULT_ALPHA, SHOW_RESULT_ALPHA)
        va.duration = VISIBILITY_TRANSITION_DURATION
        va.repeatCount = 0
        va.addUpdateListener { animation -> view.alpha = animation.animatedValue as Float }
        va.start()
    }

    private fun hideGuessResult() {
        viewModel.starteNextLocation()
        hideGuessResultAnimator(binding.guessPointsDisplay)
        hideGuessResultAnimator(binding.guessPointsLabel)
        hideGuessResultAnimator(binding.totalScoreDisplay)
        hideGuessResultAnimator(binding.totalScoreLabel)
        hideGuessResultAnimator(binding.resultRoundedCornerBackground)
    }

    private fun hideGuessResultAnimator(view: View) {
        val va = ValueAnimator.ofFloat(SHOW_RESULT_ALPHA, HIDE_RESULT_ALPHA)
        va.duration = VISIBILITY_TRANSITION_DURATION
        va.repeatCount = 0
        va.addUpdateListener { animation -> view.alpha = animation.animatedValue as Float }
        va.start()
    }

    private fun resetMapView() {
        viewModel.currentMarker.value?.remove()
        viewModel.targetMarker.value?.remove()
        viewModel.resultPolyline.value?.remove()

        map.setOnMapClickListener { latLng ->
            binding.guessButton.isEnabled = true
            viewModel.currentMarker.value?.remove()
            viewModel.updateCurrentMarker(
                map.addMarker(
                    MarkerOptions().position(latLng)
                )
            )
        }
        // reset map zoom and position
//        map.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(targetLocation, RESULT_ZOOM_LEVEL)
//        )
    }

    private fun transitionToMap() {
        binding.pinButton.isEnabled = true
        binding.mapButton.isEnabled = false

        binding.map.animate()
            .translationX(-1f)
            .setDuration(500)
            .start()
        binding.streetView.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.pager_translation_street_view_exit
            )
        )
    }

    private fun transitionToStreetView() {
        binding.pinButton.isEnabled = false
        binding.mapButton.isEnabled = true

        binding.map.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.pager_translation_map_exit)
        )
        binding.streetView.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.pager_translation_street_view_enter
            )
        )
        binding.streetView.translationX = 0f
        binding.map.translationX = binding.map.measuredWidth.toFloat()
    }

    private fun disableMapClicks() {
        map.setOnMapClickListener {
            // do nothing
        }
    }

    private fun rateGuessAccuracy() {
        val targetLocation = viewModel.streetViewLocation.value ?: DEFAULT_LAT_LNG
        val guessLocation = viewModel.currentMarker.value?.position ?: DEFAULT_LAT_LNG

        viewModel.locationGuessed(
            distanceFromTarget = calculateDistanceFromTarget(
                startLatLng = targetLocation,
                endLatLng = guessLocation,
            )
        )

        viewModel.updateTargetMarker(
            map.addMarker(
                MarkerOptions()
                    .position(targetLocation)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        )

        viewModel.updateResultPolyline(
            map.addPolyline(
                PolylineOptions()
                    .clickable(false)
                    .add(targetLocation, guessLocation)
                    .geodesic(false)
            )
        )

        val resultBounds = LatLngBounds.Builder()
            .include(targetLocation)
            .include(guessLocation)
            .build()

        map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(resultBounds, DEFAULT_RESULT_BOUNDS_PADDING_VALUE),
            object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    if (!map.projection.visibleRegion.latLngBounds.contains(targetLocation)) {
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(targetLocation, RESULT_ZOOM_LEVEL)
                        )
                    }
                }

                override fun onCancel() {
                    // do nothing
                }
            }
        )
    }

    private fun calculateDistanceFromTarget(
        startLatLng: LatLng,
        endLatLng: LatLng,
    ): Float {
        val distanceResults = FloatArray(FINAL_RESULT_ARRAY_SIZE)

        Location.distanceBetween(
            startLatLng.latitude,
            startLatLng.longitude,
            endLatLng.latitude,
            endLatLng.longitude,
            distanceResults
        )

        return distanceResults[0]
    }
}