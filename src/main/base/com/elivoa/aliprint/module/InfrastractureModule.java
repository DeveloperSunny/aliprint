package com.elivoa.aliprint.module;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.URLEncoder;

import com.elivoa.aliprint.common.exception.ExceptionProcessingModule;
import com.elivoa.aliprint.common.path.LooseURLEncoderImpl;
import com.elivoa.aliprint.common.properties.PropertiesModule;

/**
 * InfrastractureModule
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Mar 3, 2012]
 * @author bogao [elivoa|gmail.com], May 29, 2012 At HOME + IndexingModule.class <BR>
 * @author bogao [elivoa|gmail.com], Aug 20, 2012 At Tsinghua + Pub/Sub System <BR>
 */
@SubModule({
// register sub modules...
// ExceptionProcessingModule.class, //
// AjaxUploadModule.class, //
PropertiesModule.class, //
// IndexingModule.class //
})
public class InfrastractureModule {

	public static void bind(ServiceBinder binder) {

		// binder.bind(FileService.class, FileServiceImpl.class);

		// binder.bind(ProgressKeeper.class, ProgressKeeperImpl.class);

	}

	/**
	 * Service Overrides
	 */
	@Contribute(ServiceOverride.class)
	public static void setupApplicationServiceOverrides(MappedConfiguration<Class<?>, Object> configuration) {
		// override spaces in url.
		configuration.addInstance(URLEncoder.class, LooseURLEncoderImpl.class);
	}

	/**
	 * Coercers
	 */
	@Contribute(TypeCoercer.class)
	public void contributeTypeCoercer(Configuration<CoercionTuple<?, ?>> configuration) {

		// Date2Timestamp Coercer
		Coercion<Date, Timestamp> date2timestampCoercion = new Coercion<Date, Timestamp>() {
			public Timestamp coerce(Date input) {
				Timestamp t = null;
				if (null != input) {
					t = new Timestamp(input.getTime());
				} else {
					t = new Timestamp(System.currentTimeMillis());
				}
				return t;
			}
		};

		configuration.add(new CoercionTuple<Date, Timestamp>(Date.class, Timestamp.class, date2timestampCoercion));
	}

}
