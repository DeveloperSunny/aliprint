// Copyright 2008, 2010 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.elivoa.aliprint.common.path;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.internal.services.ContextPathEncoderImpl;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ContextPathEncoder;
import org.apache.tapestry5.services.ContextValueEncoder;
import org.apache.tapestry5.services.URLEncoder;

// noused.
public class CustomizedContextPathEncoderImpl extends ContextPathEncoderImpl implements ContextPathEncoder {
	// private static final int BUFFER_SIZE = 100;
	//
	// private final ContextValueEncoder valueEncoder;
	//
	// private final URLEncoder urlEncoder;
	//
	// private final TypeCoercer typeCoercer;
	//
	// private final EventContext EMPTY = new EmptyEventContext();
	//
	public CustomizedContextPathEncoderImpl(ContextValueEncoder valueEncoder, URLEncoder urlEncoder,
			TypeCoercer typeCoercer) {
		super(valueEncoder, urlEncoder, typeCoercer);
		// this.valueEncoder = valueEncoder;
		// this.urlEncoder = urlEncoder;
		// this.typeCoercer = typeCoercer;
	}

	public EventContext decodePath(String path) {
		EventContext decodePath = super.decodePath(path);
		return decodePath;
	}
}
