package fr.ekito.myweatherapp.di

import fr.ekito.myweatherapp.data.repository.WeatherRepository
import fr.ekito.myweatherapp.data.repository.WeatherRepositoryImpl
import fr.ekito.myweatherapp.util.rx.ApplicationSchedulerProvider
import fr.ekito.myweatherapp.util.rx.SchedulerProvider
import fr.ekito.myweatherapp.view.detail.DetailContract
import fr.ekito.myweatherapp.view.detail.DetailPresenter
import fr.ekito.myweatherapp.view.splash.SplashContract
import fr.ekito.myweatherapp.view.splash.SplashPresenter
import fr.ekito.myweatherapp.view.weather.*
import org.koin.dsl.ext.*
import org.koin.dsl.module.module
import org.koin.dsl.path.moduleName

/**
 * App Components
 */
val weatherAppModule = module {

    // Presenter for Search View
    factory<SplashPresenter, SplashContract.Presenter>()

    // scoped module example
    module(WeatherActivity::class.moduleName) {
        // Presenter for ResultHeader View
        single<WeatherHeaderPresenter, WeatherHeaderContract.Presenter>()

        // Presenter for ResultList View
        single<WeatherListPresenter,WeatherListContract.Presenter>()
    }

    // Presenter with injection parameter for Detail View
    factory { (id: String) ->
        DetailPresenter(id, get(), get()) as DetailContract.Presenter
    }

    // Weather Data Repository
    single<WeatherRepositoryImpl, WeatherRepository>(createOnStart = true)

    // Rx Schedulers
    single<ApplicationSchedulerProvider, SchedulerProvider>()
}

// Gather all app modules
val onlineWeatherApp = listOf(weatherAppModule, remoteDatasourceModule)
val offlineWeatherApp = listOf(weatherAppModule, localAndroidDatasourceModule)