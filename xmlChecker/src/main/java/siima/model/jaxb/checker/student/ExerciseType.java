//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.05 at 03:13:38 PM EET 
//


package siima.model.jaxb.checker.student;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exerciseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exerciseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="round" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pointsOfTestCases" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="resultsOfTestCases" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="errorsOfTestCases" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="exerciseId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exerciseType", propOrder = {
    "description",
    "round",
    "pointsOfTestCases",
    "resultsOfTestCases",
    "errorsOfTestCases"
})
public class ExerciseType {

    protected String description;
    protected String round;
    @XmlElement(type = Integer.class)
    protected List<Integer> pointsOfTestCases;
    protected List<String> resultsOfTestCases;
    protected List<String> errorsOfTestCases;
    @XmlAttribute(name = "exerciseId")
    protected String exerciseId;

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
     * Gets the value of the round property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRound() {
        return round;
    }

    /**
     * Sets the value of the round property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRound(String value) {
        this.round = value;
    }

    /**
     * Gets the value of the pointsOfTestCases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pointsOfTestCases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPointsOfTestCases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getPointsOfTestCases() {
        if (pointsOfTestCases == null) {
            pointsOfTestCases = new ArrayList<Integer>();
        }
        return this.pointsOfTestCases;
    }

    /**
     * Gets the value of the resultsOfTestCases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultsOfTestCases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultsOfTestCases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResultsOfTestCases() {
        if (resultsOfTestCases == null) {
            resultsOfTestCases = new ArrayList<String>();
        }
        return this.resultsOfTestCases;
    }

    /**
     * Gets the value of the errorsOfTestCases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorsOfTestCases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorsOfTestCases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getErrorsOfTestCases() {
        if (errorsOfTestCases == null) {
            errorsOfTestCases = new ArrayList<String>();
        }
        return this.errorsOfTestCases;
    }

    /**
     * Gets the value of the exerciseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets the value of the exerciseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExerciseId(String value) {
        this.exerciseId = value;
    }

}