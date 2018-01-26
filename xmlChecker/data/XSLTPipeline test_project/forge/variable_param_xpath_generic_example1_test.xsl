<?xml version="1.0" encoding="UTF-8"?>
<!--
variable_param_xpath_generic_example1_test.xsl
ON variable_param_xpath_generic_example1.xsl kohdistetuna production_line_hidden.xml
TULOSTAA: production_line/unit/starter_module/name: Starter Module SM1
TOIMII!!

https://stackoverflow.com/questions/4682151/xslvariable-as-xpath-value-for-other-xsl-tag
Dynamic evaluation of an XPath expression is generally NOT supported in XSLT (both 1.0 and 2.0), however:
We can implement a rather general dynamic XPath evaluator if we only restrict each location path to be an element name:
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--xsl:output method="xml" indent="yes" encoding="utf-8"/-->

<xsl:output method="text"/>

 <xsl:param name="inputId" select="'starter_module/name'"/>
 <xsl:variable name="vXpathExpression"
  select="concat('production_line/unit/', $inputId)"/>

 <xsl:template match="/">
  <xsl:value-of select="$vXpathExpression"/>: <xsl:text/>

  <xsl:call-template name="getNodeValue">
    <xsl:with-param name="pExpression"
         select="$vXpathExpression"/>
  </xsl:call-template>
 </xsl:template>

 <xsl:template name="getNodeValue">
   <xsl:param name="pExpression"/>
   <xsl:param name="pCurrentNode" select="."/>

   <xsl:choose>
    <xsl:when test="not(contains($pExpression, '/'))">
      <xsl:value-of select="$pCurrentNode/*[name()=$pExpression]"/>
    </xsl:when>
    <xsl:otherwise><!-- recursive call step by step -->
      <xsl:call-template name="getNodeValue">
        <xsl:with-param name="pExpression"
          select="substring-after($pExpression, '/')"/>
        <xsl:with-param name="pCurrentNode" select=
        "$pCurrentNode/*[name()=substring-before($pExpression, '/')]"/>
      </xsl:call-template>
    </xsl:otherwise>
   </xsl:choose>
 </xsl:template>

</xsl:stylesheet>