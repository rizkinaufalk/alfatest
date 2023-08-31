package com.rizki.alfatest.app.feature.base

import android.graphics.Rect
import android.os.Bundle
import android.text.Spanned
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import com.rizki.alfatest.ext.delegate.DisplayMessage
import com.rizki.alfatest.ext.delegate.DisplayMessageImpl
import com.rizki.alfatest.ext.delegate.EventChange
import com.rizki.alfatest.ext.delegate.EventChangeImpl
import timber.log.Timber

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>
    : Fragment(),
    EventChange by EventChangeImpl(),
    DisplayMessage by DisplayMessageImpl() {

    abstract val viewModel: VM
    abstract val binding: VB
    abstract val bindingVariable: Int

//    private val progressDialogHelper = ProgressDialogHelper()
    //
//    lateinit var prefManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        prefManager = SharedPreferenceManager(requireContext())
        setHasOptionsMenu(false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        setupFMListener()

        registerLifecycleOwner(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun performDataBinding() {
        binding.setVariable(bindingVariable, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performDataBinding()
        setupArguments()
        setupAdapter()
        setupViewPager()
        setupComponent()

        setupListener()
        setupObserver()

        initAPI()
        initOnClick()
    }

    protected open fun setupComponent() {}
    protected open fun setupArguments() {}
    protected open fun setupAdapter() {}
    protected open fun setupViewPager() {}

    protected open fun setupListener() {}
    protected open fun setupObserver() {}

    protected open fun initAPI() {}
    protected open fun initOnClick() {}


    protected open fun setupFMListener() {}

    protected open fun showLoading(){
        showLoading(requireContext())
    }

    protected open fun hiedLoading(){
        hideLoading()
    }

    /** SnackBar */
    private fun showSnackBar(message: String?, spanned: Spanned?, color: String?) {
//        var snackbar = Snackbar.make(requireContext(), this.requireView(), getString(R.string.default_error_message), Snackbar.LENGTH_SHORT)
//        message?.let { snackbar = Snackbar.make(requireContext(), this.requireView(), it, Snackbar.LENGTH_SHORT) }
//        spanned?.let { snackbar = Snackbar.make(requireContext(), this.requireView(), it, Snackbar.LENGTH_SHORT) }
//        color?.let { snackbar.view.setBackgroundColor(Color.parseColor(it)) }
//        snackbar.show()
    }

    /** Navigate */
//    fun navigate(command: NavigationCommand) {
//        when (command) {
//            is NavigationCommand.To -> findNavController().navigate(command.directions)
//            is NavigationCommand.Back -> findNavController().popBackStack()
//            is NavigationCommand.BackTo -> findNavController().popBackStack(
//                command.destinationId, false)
//        }
//    }

    /** Check Item Bank Scope */
//    fun isItemBankScopeAvailable(): Boolean {
//        val userAppScope = prefManager.userAppScope
//        return userAppScope.contains(Scope.APP_ITEM_BANK.value)
//    }

    interface OnKeyboardVisibilityListener {
        fun onVisibilityChanged(visible: Boolean)
    }

    fun setKeyboardVisibilityListener(
        onKeyboardVisibilityListener: OnKeyboardVisibilityListener,
        view: View
    ) {
        val parentView = (view as ViewGroup)
        parentView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            private var alreadyOpen = false
            private val defaultKeyboardHeightDP = 100
            private val EstimatedKeyboardDP = defaultKeyboardHeightDP + 48
            private val rect = Rect()

            override fun onGlobalLayout() {
                val estimatedKeyboardHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    EstimatedKeyboardDP.toFloat(),
                    parentView.resources.displayMetrics
                ).toInt()

                parentView.getWindowVisibleDisplayFrame(rect)
                val heightDiff = parentView.rootView.height - (rect.bottom - rect.top)
                val isShown = heightDiff >= estimatedKeyboardHeight
                if (isShown == alreadyOpen) {
                    Timber.i("Keyboard state: Ignoring global layout change...")
                    return
                }
                alreadyOpen = isShown
                onKeyboardVisibilityListener.onVisibilityChanged(isShown)
            }
        })
    }

    /** Back Press */
//    override fun onBackPressed(): Boolean {
//        return initOnBackPress(requireActivity())
//    }
}