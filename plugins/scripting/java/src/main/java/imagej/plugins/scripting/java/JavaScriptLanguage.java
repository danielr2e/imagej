/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2014 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package imagej.plugins.scripting.java;

import imagej.script.AbstractScriptLanguage;
import imagej.script.ScriptLanguage;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;

import org.scijava.plugin.Plugin;

/**
 * TODO
 * 
 * @author Johannes Schindelin
 */
@Plugin(type = ScriptLanguage.class)
public class JavaScriptLanguage extends AbstractScriptLanguage {

	// -- ScriptLanguage methods --

	@Override
	public boolean isCompiledLanguage() {
		return true;
	}

	// -- ScriptEngineFactory methods --

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("java", "xml");
	}

	@Override
	public String getEngineName() {
		return "MiniMaven";
	}

	@Override
	public List<String> getMimeTypes() {
		return Arrays.asList("application/x-java");
	}

	@Override
	public ScriptEngine getScriptEngine() {
		final JavaEngine engine = new JavaEngine();
		getContext().inject(engine);
		return engine;
	}

}
