/*
 * KeyVaultClient
 * The key vault client performs cryptographic key operations and vault operations against the Key Vault service.
 *
 * OpenAPI spec version: 2016-10-01
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.microsoft.identity.internal.test.keyvault.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * A Deleted Secret consisting of its previous id, attributes and its tags, as well as information on when it will be purged.
 */
@ApiModel(description = "A Deleted Secret consisting of its previous id, attributes and its tags, as well as information on when it will be purged.")

public class DeletedSecretBundle {
  @SerializedName("value")
  private String value = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("contentType")
  private String contentType = null;

  @SerializedName("attributes")
  private SecretAttributes attributes = null;

  @SerializedName("tags")
  private Map<String, String> tags = null;

  @SerializedName("kid")
  private String kid = null;

  @SerializedName("managed")
  private Boolean managed = null;

  public DeletedSecretBundle value(String value) {
    this.value = value;
    return this;
  }

   /**
   * The secret value.
   * @return value
  **/
  @ApiModelProperty(value = "The secret value.")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public DeletedSecretBundle id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The secret id.
   * @return id
  **/
  @ApiModelProperty(value = "The secret id.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DeletedSecretBundle contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

   /**
   * The content type of the secret.
   * @return contentType
  **/
  @ApiModelProperty(value = "The content type of the secret.")
  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public DeletedSecretBundle attributes(SecretAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

   /**
   * The secret management attributes.
   * @return attributes
  **/
  @ApiModelProperty(value = "The secret management attributes.")
  public SecretAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(SecretAttributes attributes) {
    this.attributes = attributes;
  }

  public DeletedSecretBundle tags(Map<String, String> tags) {
    this.tags = tags;
    return this;
  }

  public DeletedSecretBundle putTagsItem(String key, String tagsItem) {
    if (this.tags == null) {
      this.tags = new HashMap<String, String>();
    }
    this.tags.put(key, tagsItem);
    return this;
  }

   /**
   * Application specific metadata in the form of key-value pairs.
   * @return tags
  **/
  @ApiModelProperty(value = "Application specific metadata in the form of key-value pairs.")
  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

   /**
   * If this is a secret backing a KV certificate, then this field specifies the corresponding key backing the KV certificate.
   * @return kid
  **/
  @ApiModelProperty(value = "If this is a secret backing a KV certificate, then this field specifies the corresponding key backing the KV certificate.")
  public String getKid() {
    return kid;
  }

   /**
   * True if the secret&#39;s lifetime is managed by key vault. If this is a secret backing a certificate, then managed will be true.
   * @return managed
  **/
  @ApiModelProperty(value = "True if the secret's lifetime is managed by key vault. If this is a secret backing a certificate, then managed will be true.")
  public Boolean isManaged() {
    return managed;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeletedSecretBundle deletedSecretBundle = (DeletedSecretBundle) o;
    return Objects.equals(this.value, deletedSecretBundle.value) &&
        Objects.equals(this.id, deletedSecretBundle.id) &&
        Objects.equals(this.contentType, deletedSecretBundle.contentType) &&
        Objects.equals(this.attributes, deletedSecretBundle.attributes) &&
        Objects.equals(this.tags, deletedSecretBundle.tags) &&
        Objects.equals(this.kid, deletedSecretBundle.kid) &&
        Objects.equals(this.managed, deletedSecretBundle.managed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, id, contentType, attributes, tags, kid, managed);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeletedSecretBundle {\n");
    
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    kid: ").append(toIndentedString(kid)).append("\n");
    sb.append("    managed: ").append(toIndentedString(managed)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
