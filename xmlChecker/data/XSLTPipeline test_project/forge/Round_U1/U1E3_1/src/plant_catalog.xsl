<?xml version="1.0"  encoding="utf-8"?>
<!-- U1E3: Students' version of plant_catalog.xsl. Requires filling with correct XPath statements. -->
<!-- U1E3: Replace all '/VASTAUSn' (n=[1-10]) with a correct XPath expression. -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="text"/>

	<xsl:template match="/" mode="kysymys1">
		<xsl:text>Kysymys 1 &#xA;</xsl:text>
		<xsl:text>Dokumentin juuri:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS1"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

	<xsl:template match="/" mode="kysymys2">
		<xsl:text>Kysymys 2 &#xA;</xsl:text>
		<xsl:text>Dokumentin juurielementti:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS2">
			<xsl:value-of select="@id"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys3">
		<xsl:text>Kysymys 3 &#xA;</xsl:text>
		<xsl:text>Kaikki juurielementin lapsi-elementit:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS3">
			<xsl:value-of select="@id"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys4">
		<xsl:text>Kysymys 4 &#xA;</xsl:text>
		<xsl:text>Kaikki juurielementin lapsenlapsi-elementit:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS4">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys5">
		<xsl:text>Kysymys 5 &#xA;</xsl:text>
		<xsl:text>Kolmannen Plant:n Common-elementin sisältö:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS5"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

	<xsl:template match="/" mode="kysymys6">
		<xsl:text>Kysymys 6 &#xA;</xsl:text>
		<xsl:text>Viimeisen kasvin kasvitieteellinen nimi:&#xA;</xsl:text>
		<xsl:value-of select="/VASTAUS6"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	
	<xsl:template match="/" mode="kysymys7">
		<xsl:text>Kysymys 7 &#xA;</xsl:text>
		<xsl:text>Kaikkien niiden kasvien Zone-elementit, joiden hinta  &lt;=3.20:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS7">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys8">
		<xsl:text>Kysymys 8 &#xA;</xsl:text>
		<xsl:text>Valitsee kasvit, joiden hinta yli 6.60 ja jotka viihtyvät auringossa:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS8">
			<xsl:value-of select="Common/text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys9">
		<xsl:text>Kysymys 9 &#xA;</xsl:text>
		<xsl:text>Yksivuotisten kasvien Zone-elementtejä edeltävät sisarelementit:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS9">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys10">
		<xsl:text>Kysymys 10 &#xA;</xsl:text>
		<xsl:text>Yksivuotisten kasvien Zone-elementtien kaikki sisarelementit:&#xA;</xsl:text>
		<xsl:for-each select="/VASTAUS10">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>
	
	

	<!-- Älä muokkaa alla olevaa osiota tiedostosta mitenkään! -->

	<xsl:param name="kysymys">all</xsl:param>

	<xsl:template match="/">
		<xsl:text>&#xA;</xsl:text>
		<xsl:choose>
			<xsl:when test="$kysymys='1'">
				<xsl:apply-templates select="." mode="kysymys1"/>
			</xsl:when>
			<xsl:when test="$kysymys='2'">
				<xsl:apply-templates select="." mode="kysymys2"/>
			</xsl:when>
			<xsl:when test="$kysymys='3'">
				<xsl:apply-templates select="." mode="kysymys3"/>
			</xsl:when>
			<xsl:when test="$kysymys='4'">
				<xsl:apply-templates select="." mode="kysymys4"/>
			</xsl:when>
			<xsl:when test="$kysymys='5'">
				<xsl:apply-templates select="." mode="kysymys5"/>
			</xsl:when>
			<xsl:when test="$kysymys='6'">
				<xsl:apply-templates select="." mode="kysymys6"/>
			</xsl:when>
			<xsl:when test="$kysymys='7'">
				<xsl:apply-templates select="." mode="kysymys7"/>
			</xsl:when>
			<xsl:when test="$kysymys='8'">
				<xsl:apply-templates select="." mode="kysymys8"/>
			</xsl:when>
			<xsl:when test="$kysymys='9'">
				<xsl:apply-templates select="." mode="kysymys9"/>
			</xsl:when>
			<xsl:when test="$kysymys='10'">
				<xsl:apply-templates select="." mode="kysymys10"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="kysymys1"/>
				<xsl:apply-templates select="." mode="kysymys2"/>
				<xsl:apply-templates select="." mode="kysymys3"/>
				<xsl:apply-templates select="." mode="kysymys4"/>
				<xsl:apply-templates select="." mode="kysymys5"/>
				<xsl:apply-templates select="." mode="kysymys6"/>
				<xsl:apply-templates select="." mode="kysymys7"/>
				<xsl:apply-templates select="." mode="kysymys8"/>
				<xsl:apply-templates select="." mode="kysymys9"/>
				<xsl:apply-templates select="." mode="kysymys10"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text>***</xsl:text>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

</xsl:stylesheet>
