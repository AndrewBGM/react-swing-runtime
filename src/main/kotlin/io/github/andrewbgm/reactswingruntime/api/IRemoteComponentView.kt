package io.github.andrewbgm.reactswingruntime.api

/**
 * Represents a UI component.
 */
interface IRemoteComponentView {
  /**
   * Unique ID identifying this view.
   */
  val id: String

  /**
   * Type of this view.
   */
  val type: IRemoteComponentType
}
