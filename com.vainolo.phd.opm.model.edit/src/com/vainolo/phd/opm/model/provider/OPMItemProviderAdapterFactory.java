/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.vainolo.phd.opm.model.provider;

import com.vainolo.phd.opm.model.util.OPMAdapterFactory;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OPMItemProviderAdapterFactory extends OPMAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
   * This keeps track of the root adapter factory that delegates to this adapter factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
   * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
   * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
   * This constructs an instance.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OPMItemProviderAdapterFactory() {
    supportedTypes.add(IEditingDomainItemProvider.class);
    supportedTypes.add(IStructuredItemContentProvider.class);
    supportedTypes.add(ITreeItemContentProvider.class);
    supportedTypes.add(IItemLabelProvider.class);
    supportedTypes.add(IItemPropertySource.class);
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMObjectProcessDiagram} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMObjectProcessDiagramItemProvider opmObjectProcessDiagramItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMObjectProcessDiagram}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMObjectProcessDiagramAdapter() {
    if (opmObjectProcessDiagramItemProvider == null) {
      opmObjectProcessDiagramItemProvider = new OPMObjectProcessDiagramItemProvider(this);
    }

    return opmObjectProcessDiagramItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMObject} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMObjectItemProvider opmObjectItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMObject}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMObjectAdapter() {
    if (opmObjectItemProvider == null) {
      opmObjectItemProvider = new OPMObjectItemProvider(this);
    }

    return opmObjectItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMProcess} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMProcessItemProvider opmProcessItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMProcess}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMProcessAdapter() {
    if (opmProcessItemProvider == null) {
      opmProcessItemProvider = new OPMProcessItemProvider(this);
    }

    return opmProcessItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMLink} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMLinkItemProvider opmLinkItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMLink}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMLinkAdapter() {
    if (opmLinkItemProvider == null) {
      opmLinkItemProvider = new OPMLinkItemProvider(this);
    }

    return opmLinkItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMState} instances.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
    protected OPMStateItemProvider opmStateItemProvider;

    /**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMState}.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
    @Override
    public Adapter createOPMStateAdapter() {
    if (opmStateItemProvider == null) {
      opmStateItemProvider = new OPMStateItemProvider(this);
    }

    return opmStateItemProvider;
  }

    /**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMStructuralLinkAggregator} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMStructuralLinkAggregatorItemProvider opmStructuralLinkAggregatorItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMStructuralLinkAggregator}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMStructuralLinkAggregatorAdapter() {
    if (opmStructuralLinkAggregatorItemProvider == null) {
      opmStructuralLinkAggregatorItemProvider = new OPMStructuralLinkAggregatorItemProvider(this);
    }

    return opmStructuralLinkAggregatorItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMProceduralLink} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected OPMProceduralLinkItemProvider opmProceduralLinkItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMProceduralLink}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createOPMProceduralLinkAdapter() {
    if (opmProceduralLinkItemProvider == null) {
      opmProceduralLinkItemProvider = new OPMProceduralLinkItemProvider(this);
    }

    return opmProceduralLinkItemProvider;
  }

	/**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.OPMStructuralLink} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OPMStructuralLinkItemProvider opmStructuralLinkItemProvider;

  /**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.OPMStructuralLink}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createOPMStructuralLinkAdapter() {
    if (opmStructuralLinkItemProvider == null) {
      opmStructuralLinkItemProvider = new OPMStructuralLinkItemProvider(this);
    }

    return opmStructuralLinkItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link com.vainolo.phd.opm.model.Label} instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected LabelItemProvider labelItemProvider;

	/**
   * This creates an adapter for a {@link com.vainolo.phd.opm.model.Label}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter createLabelAdapter() {
    if (labelItemProvider == null) {
      labelItemProvider = new LabelItemProvider(this);
    }

    return labelItemProvider;
  }

	/**
   * This returns the root adapter factory that contains this factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ComposeableAdapterFactory getRootAdapterFactory() {
    return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
  }

	/**
   * This sets the composed adapter factory that contains this factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
    this.parentAdapterFactory = parentAdapterFactory;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public boolean isFactoryForType(Object type) {
    return supportedTypes.contains(type) || super.isFactoryForType(type);
  }

	/**
   * This implementation substitutes the factory itself as the key for the adapter.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
    return super.adapt(notifier, this);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object adapt(Object object, Object type) {
    if (isFactoryForType(type)) {
      Object adapter = super.adapt(object, type);
      if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
        return adapter;
      }
    }

    return null;
  }

	/**
   * This adds a listener.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void addListener(INotifyChangedListener notifyChangedListener) {
    changeNotifier.addListener(notifyChangedListener);
  }

	/**
   * This removes a listener.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
    changeNotifier.removeListener(notifyChangedListener);
  }

	/**
   * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void fireNotifyChanged(Notification notification) {
    changeNotifier.fireNotifyChanged(notification);

    if (parentAdapterFactory != null) {
      parentAdapterFactory.fireNotifyChanged(notification);
    }
  }

	/**
   * This disposes all of the item providers created by this factory. 
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void dispose() {
    if (opmObjectProcessDiagramItemProvider != null) opmObjectProcessDiagramItemProvider.dispose();
    if (opmStateItemProvider != null) opmStateItemProvider.dispose();
    if (opmObjectItemProvider != null) opmObjectItemProvider.dispose();
    if (opmProcessItemProvider != null) opmProcessItemProvider.dispose();
    if (opmStructuralLinkAggregatorItemProvider != null) opmStructuralLinkAggregatorItemProvider.dispose();
    if (opmLinkItemProvider != null) opmLinkItemProvider.dispose();
    if (opmProceduralLinkItemProvider != null) opmProceduralLinkItemProvider.dispose();
    if (opmStructuralLinkItemProvider != null) opmStructuralLinkItemProvider.dispose();
    if (labelItemProvider != null) labelItemProvider.dispose();
  }

}
