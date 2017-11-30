//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.16 at 01:27:02 PM EET 
//


package siima.model.jaxb.checker.taskflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for testCaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="testCaseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stuDir1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stuDir2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stuFile1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stuFile2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="refDir1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="refDir2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="refFile1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="refFile2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flow" type="{}flowType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testCaseType", propOrder = {
    "description",
    "points",
    "stuDir1",
    "stuDir2",
    "stuFile1",
    "stuFile2",
    "refDir1",
    "refDir2",
    "refFile1",
    "refFile2",
    "flow",
    "comment"
})
public class TestCaseType {

    protected String description;
    @XmlElement(required = true)
    protected String points;
    protected String stuDir1;
    protected String stuDir2;
    protected String stuFile1;
    protected String stuFile2;
    protected String refDir1;
    protected String refDir2;
    protected String refFile1;
    protected String refFile2;
    protected List<FlowType> flow;
    protected String comment;
    @XmlAttribute(name = "number")
    protected Integer number;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the points property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoints(String value) {
        this.points = value;
    }

    /**
     * Gets the value of the stuDir1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuDir1() {
        return stuDir1;
    }

    /**
     * Sets the value of the stuDir1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuDir1(String value) {
        this.stuDir1 = value;
    }

    /**
     * Gets the value of the stuDir2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuDir2() {
        return stuDir2;
    }

    /**
     * Sets the value of the stuDir2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuDir2(String value) {
        this.stuDir2 = value;
    }

    /**
     * Gets the value of the stuFile1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuFile1() {
        return stuFile1;
    }

    /**
     * Sets the value of the stuFile1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuFile1(String value) {
        this.stuFile1 = value;
    }

    /**
     * Gets the value of the stuFile2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuFile2() {
        return stuFile2;
    }

    /**
     * Sets the value of the stuFile2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuFile2(String value) {
        this.stuFile2 = value;
    }

    /**
     * Gets the value of the refDir1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefDir1() {
        return refDir1;
    }

    /**
     * Sets the value of the refDir1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefDir1(String value) {
        this.refDir1 = value;
    }

    /**
     * Gets the value of the refDir2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefDir2() {
        return refDir2;
    }

    /**
     * Sets the value of the refDir2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefDir2(String value) {
        this.refDir2 = value;
    }

    /**
     * Gets the value of the refFile1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFile1() {
        return refFile1;
    }

    /**
     * Sets the value of the refFile1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFile1(String value) {
        this.refFile1 = value;
    }

    /**
     * Gets the value of the refFile2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFile2() {
        return refFile2;
    }

    /**
     * Sets the value of the refFile2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFile2(String value) {
        this.refFile2 = value;
    }

    /**
     * Gets the value of the flow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowType }
     * 
     * 
     */
    public List<FlowType> getFlow() {
        if (flow == null) {
            flow = new ArrayList<FlowType>();
        }
        return this.flow;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumber(Integer value) {
        this.number = value;
    }

}
