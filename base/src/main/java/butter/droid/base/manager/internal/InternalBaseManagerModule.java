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

package butter.droid.base.manager.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import butter.droid.base.Internal;
import butter.droid.base.content.preferences.PreferencesHandler;
import butter.droid.base.manager.internal.provider.ProviderManager;
import butter.droid.base.manager.internal.vlc.VLCOptions;
import butter.droid.provider.mock.MockMovieMediaProvider;
import butter.droid.provider.subs.mock.MockSubsProvider;
import butter.droid.provider.vodo.VodoProvider;
import dagger.Module;
import dagger.Provides;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.util.VLCUtil;
import timber.log.Timber;

@Module
public class InternalBaseManagerModule {

    @Provides @Internal ProviderManager provideProviderManager(final Context context, final VodoProvider vodoProvider, final MockMovieMediaProvider moviesProvider) {
        // TODO subs - move subs to dagger
        return new ProviderManager(new MockSubsProvider(context), vodoProvider, moviesProvider);
    }

    @Provides @Internal @Nullable LibVLC provideLibVLC(Context context, PreferencesHandler preferencesHandler) {
        if (!VLCUtil.hasCompatibleCPU(context)) {
            Timber.e(VLCUtil.getErrorMsg());
            return null;
        } else {
            return new LibVLC(context, VLCOptions.getLibOptions());
        }
    }

}
