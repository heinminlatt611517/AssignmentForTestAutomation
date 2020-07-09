package com.zg.burgerjoint.mvp.presenters.impls

import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zg.burgerjoint.data.model.BurgerModel
import com.zg.burgerjoint.data.model.impls.BurgerModelImpl
import com.zg.burgerjoint.data.model.impls.MockBurgerModelImpl
import com.zg.burgerjoint.data.vos.BurgerVO
import com.zg.burgerjoint.dummy.getDummyBurgers
import com.zg.burgerjoint.mvp.views.MainView
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class MainPresenterImplTest {

    private lateinit var presenter : MainPresenterImpl

    @RelaxedMockK
    private lateinit var mView : MainView

    private lateinit var mBurgerModel : BurgerModel

    @Before
    fun setUpPresenter(){

        MockKAnnotations.init(this)

        BurgerModelImpl.init(ApplicationProvider.getApplicationContext())

        mBurgerModel=MockBurgerModelImpl

        presenter= MainPresenterImpl()

        presenter.initPresenter(mView)


    }

    @Test
    fun onTapAddToCart_callAddBurgerToCart(){
        val tappedBurger = BurgerVO()
        tappedBurger.burgerId=1
        tappedBurger.burgerImageUrl=""
        tappedBurger.burgerDescription=""
        tappedBurger.burgerName="Big Mac"

        val image=ImageView(ApplicationProvider.getApplicationContext())

        presenter.onTapAddToCart(tappedBurger,image)

        verify {
            mView.animateAddBurgerToCart(tappedBurger,image)
        }
    }

    @Test
    fun onTapCart_navigateToCartScreen(){
        presenter.onTapCart()
        verify {
            mView.navigateToCartScreen()
        }
    }

    @Test
    fun onTapBurger_navigateToBurgerDetail(){
        val tappedBurger = BurgerVO()
        tappedBurger.burgerId=1
        tappedBurger.burgerImageUrl=""
        tappedBurger.burgerDescription=""
        tappedBurger.burgerName="Big Mac"

        val image=ImageView(ApplicationProvider.getApplicationContext())

        presenter.onTapBurger(tappedBurger,image)

        verify {
            mView.navigateToBurgerDetailsScreenWithAnimation(tappedBurger.burgerId,image)
        }


    }

    @Test
    fun onUiReady_callDisplayBurgerList_callDisplayCountInCart(){
        //set up mock lifecycleOwner
        val lifeCycleOwner = mock(LifecycleOwner::class.java)

        //set up resume state
        val lifeCycleRegistry = LifecycleRegistry(lifeCycleOwner)
        lifeCycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        //mockito function for get onResume state
        `when`(lifeCycleOwner.lifecycle).thenReturn(lifeCycleRegistry)

        presenter.onUIReady(lifeCycleOwner)

        verify {
              mView.displayBurgerList(getDummyBurgers())
        }

    }
}