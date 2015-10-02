app.directive('scrollPosition', function($window) {
	return {
		scope: {
			scroll: '=scrollPosition'
		},
		link: function(scope, element, attrs) {
			var windowEl = angular.element($window);
			var handler = function() {
				scope.scroll = windowEl.scrollTop();
			}
			windowEl.on('scroll', scope.$apply.bind(scope, handler));
			handler();
		}
	};
});
app.directive('boxGridEffects', function () {
    return {
        restrict: 'A',
        scope: {
            boxGridEffects: '@'
        },
        link: function (scope, element) {
            element.on('mouseenter', function() {
                element.addClass(scope.boxGridEffects);
            });
            element.on('mouseleave', function() {
                element.removeClass(scope.boxGridEffects);
            });
        }
    };
})