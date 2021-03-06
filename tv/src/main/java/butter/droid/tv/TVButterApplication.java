/*
 * This file is part of Butter.
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Butter. If not, see <http://www.gnu.org/licenses/>.
 */

package butter.droid.tv;

import android.content.Context;
import android.support.multidex.MultiDex;
import butter.droid.base.BaseApplicationModule;
import butter.droid.base.ButterApplication;
import butter.droid.base.providers.DaggerProviderComponent;
import butter.droid.base.providers.ProviderComponent;
import butter.droid.base.utils.VersionUtils;

public class TVButterApplication extends ButterApplication {

    private TVInternalComponent component;

    @Override
    public void updateAvailable(String filePath) {
        if (!VersionUtils.isAndroidTV()) {
            super.updateAvailable(filePath);
        }
    }

    @Override public TVInternalComponent getComponent() {
        return component;
    }

    @Override protected void inject() {

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .baseApplicationModule(new BaseApplicationModule(this))
                .build();

        ProviderComponent providerComponent = DaggerProviderComponent.builder()
                .baseApplicationComponent(applicationComponent)
                .build();

        component = DaggerTVInternalComponent.builder()
                .providerComponent(providerComponent)
                .build();

        component.inject(this);
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static TVButterApplication getAppContext() {
        return (TVButterApplication) ButterApplication.getAppContext();
    }

}
