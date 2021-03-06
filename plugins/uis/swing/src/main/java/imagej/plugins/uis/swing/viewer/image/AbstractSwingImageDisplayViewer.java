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

package imagej.plugins.uis.swing.viewer.image;

import imagej.data.Dataset;
import imagej.display.Display;
import imagej.options.event.OptionsEvent;
import imagej.ui.common.awt.AWTDropTargetEventDispatcher;
import imagej.ui.common.awt.AWTInputEventDispatcher;
import imagej.ui.viewer.DisplayWindow;
import imagej.ui.viewer.image.AbstractImageDisplayViewer;

import org.scijava.event.EventHandler;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;

/**
 * A Swing image display viewer, which displays 2D planes in grayscale or
 * composite color. Intended to be subclassed by a concrete implementation that
 * provides a {@link DisplayWindow} in which the display should be housed.
 * 
 * @author Curtis Rueden
 * @author Lee Kamentsky
 * @author Grant Harris
 * @author Barry DeZonia
 */
public abstract class AbstractSwingImageDisplayViewer extends
	AbstractImageDisplayViewer implements SwingImageDisplayViewer
{

	protected AWTInputEventDispatcher dispatcher;

	@Parameter
	private EventService eventService;

	private JHotDrawImageCanvas imgCanvas;
	private SwingDisplayPanel imgPanel;

	// -- SwingImageDisplayViewer methods --

	@Override
	public JHotDrawImageCanvas getCanvas() {
		return imgCanvas;
	}

	// -- DisplayViewer methods --

	@Override
	public void view(final DisplayWindow w, final Display<?> d) {
		super.view(w, d);

		dispatcher = new AWTInputEventDispatcher(getDisplay(), eventService);

		// broadcast input events (keyboard and mouse)
		imgCanvas = new JHotDrawImageCanvas(this);
		imgCanvas.addEventDispatcher(dispatcher);

		// broadcast drag-and-drop events
		final AWTDropTargetEventDispatcher dropDispatcher =
			new AWTDropTargetEventDispatcher(getDisplay(), eventService);
		imgCanvas.addEventDispatcher(dropDispatcher);

		imgPanel = new SwingDisplayPanel(this, getWindow());
		setPanel(imgPanel);

		updateTitle();
	}

	@Override
	public SwingDisplayPanel getPanel() {
		return imgPanel;
	}

	@Override
	public Dataset capture() {
		return getCanvas().capture();
	}

	// -- Event handlers --

	@EventHandler
	protected void onEvent(@SuppressWarnings("unused") final OptionsEvent e) {
		updateLabel();
	}

}
