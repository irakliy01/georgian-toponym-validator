# Georgian Toponym Validator

This project provides a validator for ensuring the correct spelling of Georgian place names (toponyms) within JOSM.

## Overview

This validator comes in two forms:

1.  **MapCSS Validator:** A `.mapcss` file that can be easily added to JOSM as a tag checker rule. This is the recommended and currently functional option.
2.  **Java Validator (In Development):** A `.java` file intended for potential future use as a standalone JOSM plugin. Currently, it is not fully functional and is not intended for immediate use.

## MapCSS Validator (Recommended)

This validator provides immediate spelling checks for Georgian toponyms using MapCSS rules.

### Installation

1.  Open JOSM.
2.  Go to `Preferences` -> `Data Validator` -> `Tag Checker Rules`.
3.  Click the "+" (Add) button.
4.  Enter the path to the `georgian_toponyms.mapcss` file (or enter URL: `https://raw.githubusercontent.com/irakliy01/georgian-toponym-validator/refs/heads/main/validators/georgian_toponyms.mapcss`).
5.  Click `OK`.
6.  The validator will now be active.

### Usage

The validator will automatically flag potential spelling errors in Georgian place names within JOSM.

## Java Validator (In Development - Do Not Use)

**Important:** This validator is currently under development and should not be used in its current state. It is included for potential future expansion.

### Current Limitations

* The validator is currently written to be embedded within JOSM's core validator tests (for debugging purposes).
* It does not reliably detect if a node is located within Georgia.
* Lists of cities and towns are hardcoded.

### Future Development

The long-term goal for the Java validator is to:

* Convert it into a standalone JOSM plugin.
* Implement dynamic retrieval of Georgian toponyms via an official API (if available). This would ensure that the validator is always up-to-date.
* Correctly check that the nodes are within the Georgian borders.

### Why Two Validators?

The MapCSS validator offers a quick and easy way to implement basic validation. However, it is limited by its static nature. The Java validator, when fully developed, will provide more robust and dynamic validation capabilities.

### Data Source

The list of Georgian toponyms used in this validator is compiled from the following sources:

1.  **Source 1:** [GE:NAPR:BND:002:BuiltupA](https://geonetwork.napr.gov.ge/geonetwork/srv/eng/catalog.search;jsessionid=1E5BD4EC344515648111D65D8073F038#/metadata/be16a17f-96b8-43bf-a25d-bc000cb5d52d). License: Not specified.
2.  **Source 2:** [geo_supplimentary_gazetteer_2019.xlsx](https://data.humdata.org/dataset/cod-ab-geo), originating from the "administraciuli erTeulebi" dataset provided by the National Statistics Office of Georgia. License: [Creative Commons Attribution for Intergovernmental Organisations (CC BY-IGO)](https://data.humdata.org/faqs/licenses).
3.  **Source 3:** [GEO_AdminBoundaries_TabularData.xlsx](https://data.humdata.org/dataset/cod-ab-geo). License:  [Creative Commons Attribution for Intergovernmental Organisations (CC BY-IGO)](https://data.humdata.org/faqs/licenses).

**Data Modification:** The datasets were combined, and only the columns representing administrative levels 2 and 3 were extracted to create a unified list of place names.

### Contributing

Contributions to the development of the validators are welcome. If you have any suggestions or encounter any issues, please feel free to open an issue or submit a pull request.

### License

**Georgian Toponym Validator** is published under [MIT License](https://github.com/irakliy01/georgian-toponym-validator/blob/main/LICENSE)
