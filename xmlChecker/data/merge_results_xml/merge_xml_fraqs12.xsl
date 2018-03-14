<?xml version="1.0" encoding="UTF-8"?>
<!-- merge_xml_fraqs12.xsl  -->
<!-- NEW https://stackoverflow.com/questions/24735667/xalan-and-the-document-function -->
<!-- See OLD Book: XSLT p.149 ?? -->
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

  <xsl:template match="/">
	<xsl:variable name="docid" select="'1'"/>
	<xsl:element name="combinedStudents">
	   <xsl:call-template name="docContent">
			<xsl:with-param name="docid" select="$docid"/>
			<xsl:with-param name="studs" select="document(/report/resultfile[position()=$docid]/@name, document(' '))/studentSubmits/student"/>	
		</xsl:call-template>
	</xsl:element>
  </xsl:template>

  <xsl:template name="docContent">
	<xsl:param name="docid" select="'1'"/> 
	<xsl:param name="studs" select=""/>
	<xsl:param name="listIds" select="list_of_student_IDs"/>
	
	<xsl:variable name="subId" select="document(/report/resultfile[position()=$docid]/@name, document(' '))/studentSubmits/@submitId"/>
	<xsl:variable name="nexDoc" select="$docid+1"/>
	<xsl:variable name="nexStuds" select="document(/report/resultfile[position()=$nexDoc]/@name, document(' '))/studentSubmits/student"/>
	
	<xsl:choose>
		<xsl:when test="$docid='1'">
			<xsl:for-each select="$studs">
			
			<!-- new student-->
			<xsl:call-template name="newStudent">
				<xsl:with-param name="docid" select="$docid"/><!-- test -->
				<xsl:with-param name="studX" select="current()"/>
				<xsl:with-param name="subId" select="$subId"/>
			</xsl:call-template>
			</xsl:for-each>
		</xsl:when>
		<xsl:otherwise><!-- ? -->
			<xsl:for-each select="$nexStuds">
				<xsl:variable name="stuxId" select="./@studentId"/>
				<xsl:for-each select="$studs">
					<xsl:if test="not(./@studentId = $stuxId)">
						<xsl:text>&#xA;NEW ID:</xsl:text>
						<xsl:value-of select="./@studentId"/>
						
					</xsl:if>
				</xsl:for-each>
			</xsl:for-each>	
		</xsl:otherwise>
	</xsl:choose>
	
	<xsl:variable name="testIds" select="concat('12345',',','55555')"/>
	<xsl:value-of select="$testIds"/>
	<xsl:if test="contains($testIds,'55555')">
		<xsl:text>&#xA;CONTAINS:55555</xsl:text>
	</xsl:if>
	
	<xsl:for-each select="$nexStuds">
		<xsl:variable name="testIds" select="concat($testIds,',',current()/@studentId)"/>
	</xsl:for-each>
	<xsl:value-of select="$testIds"/>
	<!-- Recursion 
	<xsl:call-template name="docContent">
			<xsl:with-param name="docid" select="$docid"/>
			<xsl:with-param name="studs" select="document(/report/resultfile[position()=$docid]/@name, document(' '))/studentSubmits/student"/>	
	</xsl:call-template>
	-->
	
  </xsl:template>
  
  
  
  
  

  <xsl:template name="newStudent">
	<xsl:param name="docid" select="'1'"/> 
	<xsl:param name="studX" select=""/>
	<xsl:param name="subId" select=""/>
	<xsl:variable name="nexDoc" select="$docid+1"/>
	<!-- New Student-->
			<xsl:element name="student">
				<xsl:attribute name="studentId">
				<xsl:value-of select="$studX/@studentId"/>
				</xsl:attribute>
				<!-- exercise LOOP -->
				<xsl:for-each select="./exercise">
				<xsl:variable name="exid" select="./@exerciseId"/>
				<xsl:element name="exercise">
					<xsl:attribute name="exerciseId">
					<xsl:value-of select="./@exerciseId"/>
					</xsl:attribute>
					<xsl:element name="totalPoints">
						<xsl:attribute name="submitId">
							<xsl:value-of select="$subId"/>
						</xsl:attribute>
						<xsl:value-of select="sum(./pointsOfTestCases)"/>		  
					</xsl:element>
					<!-- access points of the next level exercise of the same student-->
					<xsl:call-template name="nextLevelExs">
						<xsl:with-param name="nexDoc" select="$nexDoc"/><!-- test -->
						<xsl:with-param name="stuid" select="$studX/@studentId"/>
						<xsl:with-param name="exid" select="./@exerciseId"/>	
					</xsl:call-template>
					
				</xsl:element>
				</xsl:for-each>
			</xsl:element>
  
  </xsl:template>
  
  <xsl:template name="nextLevelExs">
	<xsl:param name="nexDoc" select="'1'"/> 
	<xsl:param name="stuid" select=""/>
	<xsl:param name="exid" select=""/>
	<xsl:variable name="subId" select="document(/report/resultfile[position()=$nexDoc]/@name, document(' '))/studentSubmits/@submitId"/>
	<xsl:variable name="nexStuds" select="document(/report/resultfile[position()=$nexDoc]/@name, document(' '))/studentSubmits/student"/>
	<xsl:variable name="same" select="$nexStuds[@studentId = $stuid]"/>
	
				
		<xsl:for-each select="$same/exercise[@exerciseId=$exid]">
			<xsl:element name="totalPoints">
			<xsl:attribute name="submitId">
				<xsl:value-of select="$subId"/>
			</xsl:attribute>
			<xsl:value-of select="sum(./pointsOfTestCases)"/>
			</xsl:element>
		</xsl:for-each>
		
		<!-- recursive call -->
		<xsl:if test="/report/resultfile[position()=$nexDoc+1]">		
		<xsl:call-template name="nextLevelExs">
			<xsl:with-param name="nexDoc" select="$nexDoc+1"/>
			<xsl:with-param name="stuid" select="$stuid"/>
			<xsl:with-param name="exid" select="$exid"/>	
		</xsl:call-template>		
		</xsl:if>
   </xsl:template>
   
</xsl:transform>