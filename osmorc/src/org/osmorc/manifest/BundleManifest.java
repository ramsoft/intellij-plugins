/*
 * Copyright (c) 2007-2009, Osmorc Development Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright notice, this list
 *       of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this
 *       list of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *     * Neither the name of 'Osmorc Development Team' nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without specific
 *       prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.osmorc.manifest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.psi.ManifestFile;
import org.osgi.framework.Version;

import java.util.List;

/**
 * Represents a manifest of a bundle (convenience wrapper around {@link ManifestFile}).
 *
 * @author Robert F. Beeger (robert@beeger.net)
 * @author Jan Thom&auml; (janthomae@janthomae.de)
 */
public interface BundleManifest {
  /**
   * Gets the actual manifest file behind this manifest.
   */
  @NotNull
  ManifestFile getManifestFile();

  /**
   * Gets the bundle version. If there is no bundle version, returns 0.0.0
   */
  @NotNull
  Version getBundleVersion();

  /**
   * Gets the bundle symbolic name or null if this bundle has no symbolic name set.
   */
  @Nullable
  String getBundleSymbolicName();

  /**
   * Gets the bundle activator. If this bundle has no activator, returns null.
   */
  @Nullable
  String getBundleActivator();

  /**
   * Checks if this bundle exports the given package.
   *
   * @param packageSpec a package specification, e.g org.example.foo;version:=2.4.0
   * @return true, if this bundle exports the given package, false otherwise.
   */
  boolean isPackageExported(@NotNull String packageSpec);

  /**
   * Returns a name part of a Export-Package header for the given package name,
   * or null if the package is not exported by the bundle.
   */
  @Nullable
  String getExportedPackage(@NotNull String packageName);

  /**
   * Returns a list of package specs that represent the imports of this bundle.
   * Each package spec can be fed to {@link #isPackageExported(String)} of another bundle
   * to find out if it exports a package according to the given spec.
   */
  @NotNull
  List<String> getImports();

  /**
   * Returns a list of bundle specs which represent the bundles, that this bundle requires.
   * Each bundle spec can be fed to {@link #isRequiredBundle(String)} of another bundle
   * to find out if it is the required bundle.
   */
  @NotNull
  List<String> getRequiredBundles();

  /**
   * Returns a list of entries in the BundleClasspath header.
   * If the header does not exist or does not have entries, returns an empty list.
   */
  @NotNull
  List<String> getBundleClassPathEntries();

  /**
   * Checks if this bundle could satisfy the given require-bundle specification.
   *
   * @param bundleSpec the bundle specification as in the "Require-Bundle" header.
   * @return true if this bundle could satisfy the spec, false otherwise.
   */
  boolean isRequiredBundle(@NotNull String bundleSpec);

  /**
   * Returns true, if this bundle re-exports the given bundle.
   * This is the case if this bundle has a Require-Bundle header containing the given bundle
   * and the header value has the visibility-directive set to reexport.
   */
  boolean reExportsBundle(@NotNull BundleManifest otherBundle);

  /**
   * Returns true if this manifest represents a fragment bundle.
   */
  boolean isFragmentBundle();

  /**
   * Checks, if this bundle would be a potential fragment host for the given fragment bundle.
   */
  boolean isFragmentHostFor(@NotNull BundleManifest fragmentBundle);

  /**
   * A list of bundle specs which are re-exported by this bundle.
   * This method complements {@link #reExportsBundle(BundleManifest)}.
   */
  @NotNull
  List<String> getReExportedBundles();

  /**
   * Returns true if given package is imported by this Import-Package header of this bundle.
   */
  boolean isPackageImported(@NotNull String packageName);
}
