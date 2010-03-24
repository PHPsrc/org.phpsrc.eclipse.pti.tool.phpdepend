/*******************************************************************************
 * Copyright (c) 2010, Sven Kiera
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * - Neither the name of the Organisation nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package org.phpsrc.eclipse.pti.tools.phpdepend.core.metrics.elements;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class File extends AbstractElement {

	private final static Image IMAGE_PROJECT = PlatformUI.getWorkbench().getSharedImages().getImage(
			ISharedImages.IMG_OBJ_FILE);
	private final static Image IMAGE_NON_PROJECT = PlatformUI.getWorkbench().getSharedImages().getImage(
			ISharedImages.IMG_OBJ_FILE);

	protected IFile file;

	public File(IElement parent, String name, MetricResult[] results) {
		super(parent, name, results);
		Assert.isNotNull(parent);

		IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
				.findFilesForLocationURI(new java.io.File(name).toURI());
		if (files.length > 0) {
			file = files[0];
			this.name = file.getProject().getName() + " - " + file.getProjectRelativePath().toPortableString();
		}
	}

	public Image getImage() {
		if (file != null)
			return IMAGE_PROJECT;
		else
			return IMAGE_NON_PROJECT;
	}

	public IResource getResource() {
		return file;
	}

	public IMarker getFileMarker() {
		try {
			return file.createMarker(IMarker.TEXT);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
}
