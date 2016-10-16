package com.example.volodymyrdudas.dijkstraalg.config;

import android.provider.BaseColumns;

public class ConfigParams {
    public static final String DATABASE_NAME = "dijkstra.db";
    public static final int DATABASE_VERSION = 1;

    public static final String NODE_TABLE = "Node";
    public static final String NODE_NAME_COLUMN = "Name";

    public static final String TRIGGER_TABLE = "TriggerT";
    public static final String TRIGGERED_COLUMN = "Triggered";

    public static final String EDGE_TABLE = "Edge";
    public static final String FROM_NODE_COLUMN = "FromNode";
    public static final String TO_NODE_COLUMN = "ToNode";
    public static final String DISTANCE_COLUMN = "Distance";

    public static final String TRIGGER_NAME = "DijkstraTrigger";

    public static final String NODE_TABLE_CREATE_SCRIPT = "CREATE TABLE " + NODE_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NODE_NAME_COLUMN + " VARCHAR(50));";

    public static final String TRIGGER_TABLE_CREATE_SCRIPT = "CREATE TABLE " + TRIGGER_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRIGGERED_COLUMN + " BIT);";

    public static final String TRIGGER_TABLE_SCRIPT = "CREATE TABLE " + NODE_TABLE + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NODE_NAME_COLUMN + " VARCHAR(50));";

    public static final String EDGE_TABLE_CREATE_SCRIPT = "CREATE TABLE " + EDGE_TABLE + " ("
            + FROM_NODE_COLUMN + " INTEGER NOT NULL REFERENCES Node (" + BaseColumns._ID + "), "
            + TO_NODE_COLUMN + " INTEGER NOT NULL REFERENCES Node (" + BaseColumns._ID + "), "
            + DISTANCE_COLUMN + " DECIMAL (10, 3) NULL," +
            " PRIMARY KEY (" + FROM_NODE_COLUMN + " ASC, " + TO_NODE_COLUMN + " ASC) " +
            ");";
    public static final String TEST_SCRIPT = "SELECT * FROM " + NODE_TABLE + " where " + BaseColumns._ID + " > ?;\n";
    public static final String DIJKSTRA_SCRIPT =
//                    "IF EXISTS (\n" +
//                    "    SELECT *\n" +
//                    "    FROM sys.objects\n" +
//                    "    WHERE [type] = 'TR' AND [name] = '"+TRIGGER_NAME+"'\n" +
//                    "    )\n" +
//                    "    DROP TRIGGER "+TRIGGER_NAME+";\n" +
//                    "GO" +
                    "-- Runs breadth-first search from a specific node.\n" +
                    "-- @StartNode: If of node to start the search at.\n" +
                    "-- @EndNode: Stop the search when node with this id is found. Specify NULL to traverse the whole graph.\n" +
                    "CREATE TRIGGER  "+TRIGGER_NAME+" UPDATE OF " + TRIGGERED_COLUMN + " ON " + TRIGGER_TABLE + " \n" +
//                    "AS\n" +
                    "BEGIN \n" +
                            "DECLARE m_user_team_id integer;\n" +
                            "DECLARE m_projects_id integer;" +
                            "SELECT * FROM " + NODE_TABLE + " where " + BaseColumns._ID + " > ?;\n"+
//                    "    -- Automatically rollback the transaction if something goes wrong.    \n" +
//                    "    SET XACT_ABORT ON    \n" +
//                    "    BEGIN TRAN\n" +
//                    "    \n" +
//                    "    DECLARE @StartNode int = 1;\n" +
//                    "    DECLARE @EndNode int = NULL;\n" +
//
//                    "\t-- Increase performance and do not intefere with the results.\n" +
//                    "    SET NOCOUNT ON;\n" +
//                    "\n" +
//
//                    "    -- Create a temporary table for storing the estimates as the algorithm runs\n" +
//                    "\tCREATE TABLE #Nodes\n" +
//                    "\t(\n" +
//                    "\t\t" + BaseColumns._ID + " int NOT NULL PRIMARY KEY,    -- The Node " + BaseColumns._ID + "\n" +
//                    "\t\tEstimate DECIMAL(10,3) NOT NULL,    -- What is the distance to this node, so far?\n" +
//                    "\t\tPredecessor INTEGER NULL,           -- The node we came from to get to this node with this distance.\n" +
//                    "\t\tDone BIT NOT NULL                   -- Are we done with this node yet (is the estimate the final distance)?\n" +
//                    "\t);\n" +
//                    "\n" +
//
//                    "    -- Fill the temporary table with initial data\n" +
//                    "    INSERT INTO #Nodes (" + BaseColumns._ID + ", Estimate, Predecessor, Done)\n" +
//                    "    SELECT " + BaseColumns._ID + ", 9999999.999, NULL, 0 FROM Node\n" +
//                    "    \n" +
//
//                    "    -- Set the estimate for the node we start in to be 0.\n" +
//                    "    UPDATE #Nodes SET Estimate = 0 WHERE " + BaseColumns._ID + " = @StartNode\n" +
//                    "    IF @@rowcount <> 1\n" +
//                    "    BEGIN\n" +
//                    "        DROP TABLE #Nodes\n" +
//                    "        RAISERROR ('Could not set start node', 11, 1) \n" +
//                    "        ROLLBACK TRAN        \n" +
//                    "        RETURN 1\n" +
//                    "    END\n" +
//                    "\n" +
//
//                    "    DECLARE @FromNode int, @CurrentEstimate int\n" +
//                    "\n" +
//                    "    -- Run the algorithm until we decide that we are finished\n" +
//                    "    WHILE 1 = 1\n" +
//                    "    BEGIN\n" +
//
//                    "        -- Reset the variable, so we can detect getting no records in the next step.\n" +
//                    "        SELECT @FromNode = NULL\n" +
//                    "\n" +
//                    "        -- Select the " + BaseColumns._ID + " and current estimate for a node not done, with the lowest estimate.\n" +
//                    "        SELECT TOP 1 @FromNode = " + BaseColumns._ID + ", @CurrentEstimate = Estimate\n" +
//                    "        FROM #Nodes WHERE Done = 0 AND Estimate < 9999999.999\n" +
//                    "        ORDER BY Estimate\n" +
//                    "        \n" +
//                    "        -- Stop if we have no more unvisited, reachable nodes.\n" +
//                    "        IF @FromNode IS NULL OR @FromNode = @EndNode BREAK\n" +
//                    "\n" +
//                    "        -- We are now done with this node.\n" +
//                    "        UPDATE #Nodes SET Done = 1 WHERE " + BaseColumns._ID + " = @FromNode\n" +
//                    "\n" +
//                    "        -- Update the estimates to all neighbour node of this one (all the nodes\n" +
//                    "        -- there are edges to from this node). Only update the estimate if the new\n" +
//                    "        -- proposal (to go via the current node) is better (lower).\n" +
//                    "        UPDATE #Nodes\n" +
//
//                    "\t\tSET Estimate = @CurrentEstimate + e.Weight, Predecessor = @FromNode\n" +
//                    "        FROM #Nodes n INNER JOIN Edge e ON n." + BaseColumns._ID + " = e.ToNode\n" +
//                    "        WHERE Done = 0 AND e.FromNode = @FromNode AND (@CurrentEstimate + e.Weight) < n.Estimate\n" +
//                    "        \n" +
//                    "    END;\n" +
//                    "    \n" +
//
//                    "\t-- Select the results. We use a recursive common table expression to\n" +
//                    "\t-- get the full path from the start node to the current node.\n" +
//                    "\tWITH BacktraceCTE(" + BaseColumns._ID + ", Name, Distance, Path, NamePath)\n" +
//                    "\tAS\n" +
//                    "\t(\n" +
//                    "\t\t-- Anchor/base member of the recursion, this selects the start node.\n" +
//                    "\t\tSELECT n." + BaseColumns._ID + ", node.Name, n.Estimate, CAST(n." + BaseColumns._ID + " AS varchar(8000)),\n" +
//                    "\t\t\tCAST(node.Name AS varchar(8000))\n" +
//                    "\t\tFROM #Nodes n JOIN Node node ON n." + BaseColumns._ID + " = node." + BaseColumns._ID + "\n" +
//                    "\t\tWHERE n." + BaseColumns._ID + " = @StartNode\n" +
//                    "\t\t\n" +
//                    "\t\tUNION ALL\n" +
//                    "\t\t\n" +
//                    "\t\t-- Recursive member, select all the nodes which have the previous\n" +
//                    "\t\t-- one as their predecessor. Concat the paths together.\n" +
//                    "\t\tSELECT n." + BaseColumns._ID + ", node.Name, n.Estimate,\n" +
//                    "\t\t\tCAST(cte.Path + ',' + CAST(n." + BaseColumns._ID + " as varchar(10)) as varchar(8000)),\n" +
//                    "\t\t\tCAST(cte.NamePath + ',' + node.Name AS varchar(8000))\n" +
//                    "\t\tFROM #Nodes n JOIN BacktraceCTE cte ON n.Predecessor = cte." + BaseColumns._ID + "\n" +
//                    "\t\tJOIN Node node ON n." + BaseColumns._ID + " = node." + BaseColumns._ID + "\n" +
//                    "\t)\n" +
//                    "\tSELECT " + BaseColumns._ID + ", Name, Distance, Path, NamePath FROM BacktraceCTE\n" +
//                    "\tWHERE " + BaseColumns._ID + " = @EndNode OR @EndNode IS NULL -- This kind of where clause can potentially produce\n" +
//                    "\tORDER BY " + BaseColumns._ID + "\t\t\t\t\t\t\t\t-- a bad execution plan, but I use it for simplicity here.\n" +
//                    "    \n" +
//                    "    DROP TABLE #Nodes\n" +
//                    "    COMMIT TRAN\n" +
//                    "    RETURN 0\n" +
                    " END;";

}
