angular.module('trades', ['ngResource', 'ui.bootstrap']).
    factory('Trades', function ($resource) {
        return $resource('trades');
    }).
    factory('Trade', function ($resource) {
        return $resource('trades/:id', {id: '@id'});
    }).
    factory("EditorStatus", function () {
        var editorEnabled = {};

        var enable = function (id, fieldName) {
            editorEnabled = { 'id': id, 'fieldName': fieldName };
        };

        var disable = function () {
            editorEnabled = {};
        };

        var isEnabled = function(id, fieldName) {
            return (editorEnabled['id'] == id && editorEnabled['fieldName'] == fieldName);
        };

        return {
            isEnabled: isEnabled,
            enable: enable,
            disable: disable
        }
    });

function TradesController($scope, $modal, Trades, Trade, Status) {
    function list() {
        $scope.trades = Trades.query();
    }

    function clone (obj) {
        return JSON.parse(JSON.stringify(obj));
    }

    function saveTrade(trade) {
        Trades.save(trade,
            function () {
                Status.success("Trade saved");
                list();
            },
            function (result) {
                Status.error("Error saving trade: " + result.status);
            }
        );
    }

    $scope.addTrade = function () {
        var addModal = $modal.open({
            templateUrl: 'templates/tradeForm.html',
            controller: TradeModalController,
            resolve: {
                trade: function () {
                    return {};
                },
                action: function() {
                    return 'add';
                }
            }
        });

        addModal.result.then(function (trade) {
            saveTrade(trade);
        });
    };

    $scope.updateTrade = function (trade) {
        var updateModal = $modal.open({
            templateUrl: 'templates/tradeForm.html',
            controller: TradeModalController,
            resolve: {
                trade: function() {
                    return clone(trade);
                },
                action: function() {
                    return 'update';
                }
            }
        });

        updateModal.result.then(function (trade) {
            saveTrade(trade);
        });
    };

    $scope.deleteTrade = function (trade) {
        Trade.delete({id: trade.id},
            function () {
                Status.success("Trade deleted");
                list();
            },
            function (result) {
                Status.error("Error deleting trade: " + result.status);
            }
        );
    };

    $scope.setTradesView = function (viewName) {
        $scope.tradesView = "templates/" + viewName + ".html";
    };

    $scope.init = function() {
        list();
        $scope.setTradesView("list");
        $scope.sortField = "name";
        $scope.sortDescending = false;
    };
}

function TradeModalController($scope, $modalInstance, trade, action) {
    $scope.tradeAction = action;
    $scope.yearPattern = /^[1-2]\d{3}$/;
    $scope.trade = trade;

    $scope.ok = function () {
        $modalInstance.close($scope.trade);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

function TradeEditorController($scope, Trades, Status, EditorStatus) {
    $scope.enableEditor = function (trade, fieldName) {
        $scope.newFieldValue = trade[fieldName];
        EditorStatus.enable(trade.id, fieldName);
    };

    $scope.disableEditor = function () {
        EditorStatus.disable();
    };

    $scope.isEditorEnabled = function (trade, fieldName) {
        return EditorStatus.isEnabled(trade.id, fieldName);
    };

    $scope.save = function (trade, fieldName) {
        if ($scope.newFieldValue === "") {
            return false;
        }

        trade[fieldName] = $scope.newFieldValue;

        Trades.save({}, trade,
            function () {
                Status.success("Trade saved");
                list();
            },
            function (result) {
                Status.error("Error saving trade: " + result.status);
            }
        );

        $scope.disableEditor();
    };

    $scope.disableEditor();
}

angular.module('trades').
    directive('inPlaceEdit', function () {
        return {
            restrict: 'E',
            transclude: true,
            replace: true,

            scope: {
                ipeFieldName: '@fieldName',
                ipeInputType: '@inputType',
                ipeInputClass: '@inputClass',
                ipePattern: '@pattern',
                ipeModel: '=model'
            },

            template:
                '<div>' +
                    '<span ng-hide="isEditorEnabled(ipeModel, ipeFieldName)" ng-click="enableEditor(ipeModel, ipeFieldName)">' +
                        '<span ng-transclude></span>' +
                    '</span>' +
                    '<span ng-show="isEditorEnabled(ipeModel, ipeFieldName)">' +
                        '<div class="input-append">' +
                            '<input type="{{ipeInputType}}" name="{{ipeFieldName}}" class="{{ipeInputClass}}" ' +
                                'ng-required ng-pattern="{{ipePattern}}" ng-model="newFieldValue" ' +
                                'ui-keyup="{enter: \'save(ipeModel, ipeFieldName)\', esc: \'disableEditor()\'}"/>' +
                            '<div class="btn-group btn-group-xs" role="toolbar">' +
                                '<button ng-click="save(ipeModel, ipeFieldName)" type="button" class="btn"><span class="glyphicon glyphicon-ok"></span></button>' +
                                '<button ng-click="disableEditor()" type="button" class="btn"><span class="glyphicon glyphicon-remove"></span></button>' +
                            '</div>' +
                        '</div>' +
                    '</span>' +
                '</div>',

            controller: 'TradeEditorController'
        };
    });
