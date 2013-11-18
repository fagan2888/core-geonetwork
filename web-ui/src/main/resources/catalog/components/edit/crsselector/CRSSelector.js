(function() {
  goog.provide('gn_crs_selector');

  var module = angular.module('gn_crs_selector', []);

  /**
   *
   *
   */
  module.directive('gnCrsSelector',
      ['$rootScope', '$timeout', '$http',
        'gnMetadataManagerService',
        function($rootScope, $timeout, $http,
            gnMetadataManagerService) {

         return {
           restrict: 'A',
           replace: true,
           transclude: true,
           scope: {
             mode: '@gnCrsSelector',
             elementName: '@',
             elementRef: '@',
             domId: '@'
           },
           templateUrl: '../../catalog/components/edit/' +
           'crsselector/partials/' +
           'crsselector.html',
           link: function(scope, element, attrs) {
             scope.snippet = null;
             scope.snippetRef = gnMetadataManagerService.
             buildXMLFieldName(scope.elementRef, scope.elementName);

             scope.add = function() {
               // {$parentEditInfo/@ref}, '{concat(@prefix, ':', @name)}',
               //               '{$id}', 'before'
             };


             scope.search = function() {
               $http.get('crs.search@json?type=&maxResults=50&name=' +
                    scope.filter).success(
                   function(data) {
                     scope.crsResults = data;
                   });
             };

             // Then register search filter change
             scope.$watch('filter', scope.search);

             scope.addCRS = function(crs) {
               scope.snippet = gnMetadataManagerService.buildCRSXML(crs);
               scope.crsResults = [];

               $timeout(function() {
                 // Save the metadata and refresh the form
                 $rootScope.$broadcast('SaveEdits', true);
               });

               return false;
             };
           }
         };
       }]);
})();
